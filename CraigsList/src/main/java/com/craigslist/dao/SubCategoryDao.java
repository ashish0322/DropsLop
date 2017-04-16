/**
 * 
 */
package com.craigslist.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.craigslist.model.SubCategory;
import com.craigslist.model.User;

/**
 * @author amaheedhara
 *
 */
@Repository
@Transactional
public class SubCategoryDao {
	
	@PersistenceContext
	  private EntityManager entityManager;
	
	
	/**
	   * Save the sub category in the database.
	   */
	  public void create(SubCategory subCategory) {
	    entityManager.persist(subCategory);
	    return;
	  }

	  
	  // get list of categories

	  public List<SubCategory> getSubCategories(){
		  
		  List<SubCategory> subCategoryList = new ArrayList<>();
		  String query = "select c from SubCategory c";
		  try{
			  subCategoryList = entityManager.createQuery(query).getResultList();
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return subCategoryList;
	  }
	  
	  
	  /**
	   * Return the sub category having the passed id.
	   */
	  public SubCategory getById(long id) {
	    return entityManager.find(SubCategory.class, id);
	  }
	  
	  /**
	   * Return the sub category having the passed name.
	   */
	  public SubCategory getByName(String name) {
		  return (SubCategory) entityManager.createQuery(
			        "from SubCategory where name = :name")
			        .setParameter("name", name)
			        .getSingleResult();
	  }
	  
	  
	
	  
	  /**
	   * Update the passed sub Category in the database.
	   */
	  public SubCategory update(SubCategory subCategory) {
		 		  
		  return entityManager.merge(subCategory);
	    
	  }
	  
	  
	  /**
	   * Delete the sub Category from the database.
	   */
	  public void delete(SubCategory subCategory) {
	    if (entityManager.contains(subCategory))
	      entityManager.remove(subCategory);
	    else
	      entityManager.remove(entityManager.merge(subCategory));
	    return;
	  }
	  
	  
	  /**
	   * Validate sub category existence from the database.
	   */
	  public SubCategory getSubCategoryByName(String name) {
		    return (SubCategory) entityManager.createQuery(
		        "from SubCategory where name = :name")
		        .setParameter("name", name)
		        .getSingleResult();
		  }
}
