/**
 * 
 */
package com.craigslist.controllerImplementation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craigslist.controller.CategoryService;
import com.craigslist.dao.CategoryDao;
import com.craigslist.model.Category;

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
	@RequestMapping(value="/api/addCategory",method = RequestMethod.POST,
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
	public String updateCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCategory(long categoryId) {
		// TODO Auto-generated method stub
		return null;
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
	@RequestMapping(value="/api/editCategory/{id}",method = RequestMethod.GET)
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

	
	
}