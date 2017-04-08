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

import com.craigslist.model.Category;
import com.craigslist.model.User;

/**
 * @author amaheedhara
 *
 */
@Repository
@Transactional
public class CategoryDao  {
	
	@PersistenceContext
	  private EntityManager entityManager;
	
	
	/**
	   * Save the category in the database.
	   */
	  public void create(Category category) {
	    entityManager.persist(category);
	    return;
	  }
	  
	  // get list of categories

	  public List<Category> getCategories(){
		  
		  List<Category> categoryList = new ArrayList<>();
		  String query = "select c from Category c";
		  try{
		  categoryList = entityManager.createQuery(query).getResultList();
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return categoryList;
	  }
	  
	  /**
	   * Return the category having the passed id.
	   */
	  public Category getById(long id) {
	    return entityManager.find(Category.class, id);
	  }
	  
	  /**
	   * Return the category having the passed id.
	   */
	  public Category getByTitle(String title) {
		  
		  String query = "select c from Category where c.title :title";
	    return (Category) entityManager.createQuery(query).getSingleResult();
	  }
	  /**
	   * Update the passed Category in the database.
	   */
	  public Category update(Category category) {
		 		  
		  return entityManager.merge(category);
	    
	  }
	  
	  
	  /**
	   * Delete the Category from the database.
	   */
	  public void delete(Category category) {
	    if (entityManager.contains(category))
	      entityManager.remove(category);
	    else
	      entityManager.remove(entityManager.merge(category));
	    return;
	  }

	  /**
	   * Validate category existence from the database.
	   */
	  public Category getCategoryByName(String title) {
		    return (Category) entityManager.createQuery(
		        "from Category where title = :title")
		        .setParameter("title", title)
		        .getSingleResult();
		  }
	  
}
