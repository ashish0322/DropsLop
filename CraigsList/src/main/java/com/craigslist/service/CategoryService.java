/**
 * 
 */
package com.craigslist.service;

import java.util.List;

import com.craigslist.model.Category;

/**
 * @author amaheedhara
 *
 */
public interface CategoryService {
	
	public String addCategory(Category category);
	public String updateCategory(Category category,long categoryId);
	public Category getCategory(long id);
	public String deleteCategory(long categoryId);
	public List<Category> getCategories();
	
	public String validateCategoryName(String category);

}
