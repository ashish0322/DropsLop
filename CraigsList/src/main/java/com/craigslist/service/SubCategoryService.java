/**
 * 
 */
package com.craigslist.service;

import java.util.List;
import java.util.Set;

import com.craigslist.model.SubCategory;

/**
 * @author amaheedhara
 *
 */
public interface SubCategoryService {

	public String addSubCategory(SubCategory SubCategory,long categoryId);
	public String updateSubCategory(SubCategory SubCategory,long SubCategoryId);
	public SubCategory getSubCategory(long id);
	public String deleteSubCategory(long SubCategoryId);
	public Set<SubCategory> getSubCategories(long categoryId);
	public List<SubCategory> getSubCategoriesList();
	
	public String validateSubCategoryName(String SubCategory);
	
}
