/**
 * 
 */
package com.craigslist.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	@RequestMapping(value="/api/user/{userName}/postAd",method = RequestMethod.POST,
	consumes = "application/json")
	public String submitAd(@RequestBody Product product, @PathVariable("userName") String userName) {
		
		User user = null;
		SubCategory subCategory = null;
		
		 try {
		    user=	userDao.getByEmail(userName);
		    subCategory = subCategoryDao.getByName(product.getSubCategoryName());
		    }
		    catch (Exception ex) {
		    	 return null;
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
			
			String photoName = "Not yet uploaded";
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

}
