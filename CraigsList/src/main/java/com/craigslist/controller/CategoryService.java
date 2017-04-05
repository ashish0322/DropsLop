/**
 * 
 */
package com.craigslist.controller;

import java.util.List;

import com.craigslist.model.Category;

/**
 * @author amaheedhara
 *
 */
public interface CategoryService {
	
	public String addCategory(Category category);
	public String updateCategory(Category category);
	public Category getCategory(long id);
	public String deleteCategory(long categoryId);
	public List<Category> getCategories();

}
