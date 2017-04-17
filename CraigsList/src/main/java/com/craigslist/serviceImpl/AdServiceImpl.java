/**
 * 
 */
package com.craigslist.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.craigslist.dao.ProductDao;
import com.craigslist.dao.SubCategoryDao;
import com.craigslist.dao.UserDao;
import com.craigslist.model.Category;
import com.craigslist.model.Product;
import com.craigslist.model.SubCategory;
import com.craigslist.model.User;
import com.craigslist.model.UserInfo;
import com.craigslist.service.AdService;
import com.craigslist.util.PasswordEncrypt;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author amaheedhara
 *
 */
@RestController
public class AdServiceImpl implements AdService{
	
	 // Wire the UserDao used inside this controller.
	  @Autowired
	  private UserDao userDao;
	  
	  @Autowired
	  private ProductDao productDao;
	  
	  @Autowired
	  private SubCategoryDao subCategoryDao;

	@Override
	@RequestMapping(value="/api/user/{userName}/postAd",method = RequestMethod.POST)
	public String submitAd(@RequestParam(value="ad") String productt, 
						   @PathVariable("userName") String userName,
						   @RequestParam("file") MultipartFile filee,
						   HttpServletRequest request) {
		
		Product product = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			 product = mapper.readValue(productt, Product.class);
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		User user = null;
		SubCategory subCategory = null;
		
		 try {
		    user=	userDao.getByEmail(userName);
		    subCategory = subCategoryDao.getByName(product.getSubCategoryName());
		    }
		    catch (Exception ex) {
		    	 return null;
		    }
		
		 
		 String photoName = null;
			File file;
	        String check = File.separator; //Checking if system is linux based or windows based by checking seprator used.
	        String path = null;
	        if (check.equalsIgnoreCase("\\")) {
	            path = request.getSession().getServletContext().getRealPath("").replace("build\\", ""); //Netbeans projects gives real path as Lab6/build/web/ so we need to replace build in the path.
	            System.out.println(request.getSession().getServletContext().getRealPath(""));
	            
	            path += "\\uploads\\";
	            System.out.println(path);
	        }

	        if (check.equalsIgnoreCase("/")) {
	            path = request.getSession().getServletContext().getRealPath("").replace("build/", "");
	            path += "/"; //Adding trailing slash for Mac systems.

	        }
	        
	        if (filee != null) {

	            String fileNameWithExt = System.currentTimeMillis() + filee.getOriginalFilename();
	            file = new File(path + fileNameWithExt);
	            String context = request.getSession().getServletContext().getContextPath();
	            photoName = (context + "/" +"uploads/"+ fileNameWithExt);
	            try {
					filee.transferTo(file);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        
	        } 
		
		try {
	    	
			Category cat = product.getCategory();
			String categoryName = cat.getTitle();
	    	// Create an instance of SimpleDateFormat used for formatting 
			// the string representation of date (month/day/year)
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String stringDate = df.format(today);
		  // encrypting password using secure hash algorithm
			
			
			String sellerName = user.getFirstName();
			String approvalStatus = "Pending";
			Product newAd = new Product(product.getTitle(), product.getPurchasedYear(), product.getAddress(), 
					product.getProductName(), categoryName, product.getPrice(), product.getDescription(), photoName, 
					product.getSubCategoryName(),user.getEmail() ,sellerName,user,subCategory,approvalStatus,stringDate,product.getContact());
		
			productDao.create(newAd);
			user.addProduct(newAd);
			subCategory.addProduct(product);
			subCategoryDao.update(subCategory);
			userDao.update(user);
			
		
	    }
	    catch (Exception ex) {
	      return JSONObject.quote("Error submitting the ad: " + ex.toString());
	    }
	    return JSONObject.quote("Ad Submitted Successfully");
	}

	@Override
	@RequestMapping(value = "/api/admin/getAds", method = RequestMethod.GET)
	public List<Product> getProducts() {

		List<Product> productsList = new ArrayList<>();
		
		productsList = productDao.getProductsList();
				if(!productsList.isEmpty()){
					return productsList;
				}
				else{
					return null;
				}
	}

	@Override
	@RequestMapping(value = "/api/admin/{action}/approveAdd/{id}", method = RequestMethod.GET)
	public Product approveAd(@PathVariable String action,@PathVariable long id) {
		
		
		Product product = null;
		 try {
			 product = productDao.getById(id);
		      if(action.equalsIgnoreCase("pending")){
		 		 product.setApprovalStatus("approved");
		      }
		 	  
		      product = productDao.update(product);
		 }
		 catch (Exception ex) {
		      return product;
		    }
		
		 
		return product;
	}

	@Override
	@RequestMapping(value = "/api/admin/{id}/getAd", method = RequestMethod.GET)
	public Product getAd(@PathVariable long id) {
		

		Product product = null;
		 try {
			 product = productDao.getById(id);
		 }
		 catch (Exception ex) {
		      return product;
		    }
		
		 
		return product;
	}

	@Override
	@RequestMapping(value = "/api/admin/{id}/deleteAd", method = RequestMethod.GET)
	public String removeAd(@PathVariable long id) {
		Product product = null;
		try{
			product = productDao.getById(id);
			product.setApprovalStatus("expired");
			
	        product = productDao.update(product);
		}
		catch(Exception e){
			return JSONObject.quote("Error updating the Ad: " + e.toString());
		}
		
		return JSONObject.quote("Ad removed Successfully");
	}
	
	@Override
	@RequestMapping(value = "/api/user/{id}/getAd", method = RequestMethod.GET)
	public Product getAdByUser(@PathVariable long id) {
		

		Product product = null;
		 try {
			 product = productDao.getById(id);
		 }
		 catch (Exception ex) {
		      return product;
		    }
		
		 
		return product;
	}

	@Override
	@RequestMapping(value = "/api/user/{id}/deleteAd", method = RequestMethod.GET)
	public String removeAdByUser(@PathVariable long id) {
		Product product = null;
		try{
			product = productDao.getById(id);
			product.setApprovalStatus("expired");
			
	        product = productDao.update(product);
		}
		catch(Exception e){
			return JSONObject.quote("Error updating the Ad: " + e.toString());
		}
		
		return JSONObject.quote("Ad removed Successfully");
	}
	
	

	@Override
	@RequestMapping(value = "/api/user/{userName}/getPostings", method = RequestMethod.GET)
	public List<Product> getUserPostings(@PathVariable String userName) {
		User user = null;
		 
		List<Product> productsList = new ArrayList<>();
		
		productsList = productDao.getProductsListByUser(userName);
				if(!productsList.isEmpty()){
					return productsList;
				}
				else{
					return null;
				}
		
	}

	@Override
	@RequestMapping(value = "/api/admin/getPendingAdsCount", method = RequestMethod.GET)
	public long getPendingAds() {
		long pendingCount = 0;
		
		pendingCount = productDao.getPendingAdCount();
		
		return pendingCount;
	}

	@Override
	@RequestMapping(value = "/api/getApprovedAds", method = RequestMethod.GET)
	public List<Product> getApprovedProducts() {
		
		List<Product> productsList = new ArrayList<>();
				
				productsList = productDao.getApprovedProductsList();
						if(!productsList.isEmpty()){
							return productsList;
						}
						else{
							return null;
						}
			}
	
	
	/**
	 * Api update Ad
	 *
	*/


	@Override
	@RequestMapping(value="/api/user/{productId}/updateAd",method = RequestMethod.POST,
	consumes = "application/json")
	public String updateAd(@RequestBody Product product, @PathVariable long productId) {
			
			Product modifyProduct = new Product();
		try{
			modifyProduct = productDao.getById(productId);
			modifyProduct.setTitle(product.getTitle());
			modifyProduct.setPrice(product.getPrice());
			modifyProduct.setContact(product.getContact());
			}
			catch(Exception e){
			e.printStackTrace();
			}
		
		try{
			productDao.update(modifyProduct);
		}
		catch (Exception ex) {
		      return JSONObject.quote("Error updating the category: " + ex.toString());
	    }
	    return JSONObject.quote("Ad updated Successfully");
		
	}

}
