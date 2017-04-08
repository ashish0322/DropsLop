/**
 * 
 */
package com.craigslist.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craigslist.dao.CategoryDao;
import com.craigslist.model.Category;
import com.craigslist.service.CategoryService;

/**
 * @author amaheedhara
 *
 */
@RestController
public class CategoryServiceImpl implements CategoryService{
	
	
	// Wire the UserDao used inside this controller.
		  @Autowired
		  private CategoryDao categoryDao;
	

	@Override
	@RequestMapping(value="/api/admin/addCategory",method = RequestMethod.POST,
	consumes = "application/json")
	public String addCategory(@RequestBody Category category) {
		
		  System.out.println("Category Name is"+category.getTitle());
		    try {
		      Category newCategory = new Category(category.getTitle(), category.getDescription());
		      categoryDao.create(newCategory);
		    }
		    catch (Exception ex) {
		      return JSONObject.quote("Error creating the category: " + ex.toString());
		    }
		    return JSONObject.quote("Category added Successfully");
	}

	@Override
	@RequestMapping(value="/api/admin/{categoryId}/updateCategory",method = RequestMethod.POST,
	consumes = "application/json")
	public String updateCategory(@RequestBody Category category, @PathVariable long categoryId) {
			Category  modifyCategory = new Category();
			
		try{
			modifyCategory = categoryDao.getById(categoryId);
			modifyCategory.setTitle(category.getTitle());
			modifyCategory.setDescription(category.getDescription());
			}
			catch(Exception e){
			e.printStackTrace();
			}
		
		try{
			categoryDao.update(modifyCategory);
		}
		catch (Exception ex) {
		      return JSONObject.quote("Error updating the category: " + ex.toString());
	    }
	    return JSONObject.quote("Category updated Successfully");
		
	}

	@Override
	@RequestMapping(value="/api/admin/{categoryId}/deleteCategory",method = RequestMethod.GET)
	public String deleteCategory(@PathVariable long categoryId) {
		Category category = null;
		try{
		category = categoryDao.getById(categoryId);
		}
		catch(Exception e){
		e.printStackTrace();
		}
		
		try{
		categoryDao.delete(category);
		}
		catch(Exception ex){
			return JSONObject.quote("Error updating the category: " + ex.toString());
		}
		return JSONObject.quote("Category deleted Successfully");
	}

	@Override
	@RequestMapping(value="/api/getCategories",method = RequestMethod.GET)
	public List<Category> getCategories() {
		
		List<Category> categoryList = new ArrayList<>();
		
		categoryList = categoryDao.getCategories();
		if(!categoryList.isEmpty()){
			return categoryList;
		}
		else{
			return null;
		}
	}

	@Override
	@RequestMapping(value="/api/admin/{id}/editCategory",method = RequestMethod.GET)
	public Category getCategory(@PathVariable long id) {
		Category category = null;
		try{
		category = categoryDao.getById(id);
		}
		catch(Exception e){
		e.printStackTrace();
		}
		return category;
	}

	@Override
	@RequestMapping(value="/api/admin/validateCategory/{category}",method = RequestMethod.GET)
	public String validateCategoryName(@PathVariable String category) {
		// TODO Auto-generated method stub
		
		 try {
		    	categoryDao.getCategoryByName(category);
		    }
		    catch (Exception ex) {
		    	 return null;
		    }
		
		 return JSONObject.quote("Category already present");
	}

	
	
}
