/**
 * 
 */
package com.craigslist.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craigslist.dao.CategoryDao;
import com.craigslist.dao.SubCategoryDao;
import com.craigslist.model.Category;
import com.craigslist.model.SubCategory;
import com.craigslist.service.SubCategoryService;

/**
 * @author amaheedhara
 *
 */
@RestController
public class SubCategoryServiceImpl implements SubCategoryService{
	
	// Inject the SubCategoryDao inside this controller.
	  @Autowired
	  private SubCategoryDao subCategoryDao;
	  
	// Inject the CategoryDao used inside this controller.
	  @Autowired
	  private CategoryDao categoryDao;

	/**
	 * Api add SubCategory
	 *
	*/
	@Override
	@RequestMapping(value="/api/admin/{categoryId}/addSubCategory",method = RequestMethod.POST,
	consumes = "application/json")
	public String addSubCategory(@RequestBody SubCategory subCategory,@PathVariable long categoryId) {
		
		Category category = null;
		try{
		category = categoryDao.getById(categoryId);
		}
		catch(Exception e){
		e.printStackTrace();
		}
		
	 System.out.println("Sub Category Name is"+subCategory.getName());
	 
	    try {
	    	SubCategory newSubCategory = new SubCategory(subCategory.getName(), category);
	    	subCategoryDao.create(newSubCategory);
	    	category.addSubCategory(newSubCategory);
	    	categoryDao.update(category);
	    }
	    catch (Exception ex) {
	      return JSONObject.quote("Error creating the sub category: " + ex.toString());
	    }
	    return JSONObject.quote("Sub Category added Successfully");

	}

	@Override
	public String updateSubCategory(SubCategory SubCategory, long SubCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubCategory getSubCategory(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteSubCategory(long SubCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(value="/api/getSubCategories/{categoryId}",method = RequestMethod.GET)
	public Set<SubCategory> getSubCategories(@PathVariable long categoryId) {
		
		Category category = null;
		try{
		category = categoryDao.getById(categoryId);
		}
		catch(Exception e){
		e.printStackTrace();
		}
		
		Set<SubCategory> subCategoryList = category.getSubCategories();
		return subCategoryList;
		
	}

	
	@Override
	@RequestMapping(value="/api/getSubCategories",method = RequestMethod.GET)
	public List<SubCategory> getSubCategoriesList() {
		List<SubCategory> subCategoryList = null;

		try{
			subCategoryList = subCategoryDao.getSubCategories();
		}
		catch(Exception e){
		e.printStackTrace();
		}
		return subCategoryList;
		
	}
	
	@Override
	@RequestMapping(value="/api/admin/validateSubCategory/{subCategory}",method = RequestMethod.GET)
	public String validateSubCategoryName(@PathVariable String subCategory) {
		SubCategory subCat = null;
		 try {
		    	subCat = subCategoryDao.getSubCategoryByName(subCategory);
		    }
		    catch (Exception ex) {
		    	 return null;
		    }
		if(subCat != null){
		 return JSONObject.quote("Sub Category already present");
		}
		else{
			return null;
		}
	}
	
	

}
