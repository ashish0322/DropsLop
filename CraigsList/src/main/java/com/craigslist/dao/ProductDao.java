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

import com.craigslist.model.Product;

/**
 * @author amaheedhara
 *
 */
@Repository
@Transactional
public class ProductDao {
	
	
	 // An EntityManager will be automatically injected from entityManagerFactory
	  // setup on DatabaseConfig class.
	  @PersistenceContext
	  private EntityManager entityManager;
	  
	  
	  /**
	   * Save the product in the database.
	   */
	  public void create(Product product) {
	    entityManager.persist(product);
	    return;
	  }
	  
	  
	//get list of products

	  public List<Product> getProductsList(){
		  
		  List<Product> productsList = new ArrayList<>();
		  String query = "select p from Product p";
		  try{
			  productsList = entityManager.createQuery(query).getResultList();
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return productsList;
	  }
 
	//get list of products by user
	  public List<Product> getProductsListByUser(String sellerEmail){
		  
		  String query = "from Product where sellerEmail = :sellerEmail";
		  
		  List<Product> productsList = new ArrayList<>();
		  
		  try{
			  productsList = entityManager
					  			.createQuery(query)
					  			.setParameter("sellerEmail", sellerEmail)
					  			.getResultList();
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return productsList;
		  
	  }

	  /**
	   * Return the product having the passed id.
	   */
	  public Product getById(long id) {
	    return entityManager.find(Product.class, id);
	  }
	  
	  /**
	   * Update the passed product in the database.
	   */
	  public Product update(Product product) {
		  return entityManager.merge(product);
	   
	  }

}
