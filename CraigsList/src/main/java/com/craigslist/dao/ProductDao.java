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
		  String query = "from Product";
		  try{
			  productsList = entityManager.createQuery(query)
					  
			  			.getResultList();
					  
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return productsList;
	  }
	  
	  public List<Product> getProductsList1(){
		  String approvalStatus = "approved";
		 
		  
		  List<Product> productsList = new ArrayList<>();
		  String query = "from Product where approvalStatus = :approvalStatus";
		  try{
			  productsList = entityManager.createQuery(query)
					  .setParameter("approvalStatus", approvalStatus)
			  			.getResultList();
					  
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
	   * Return the pending ads count
	   */
	  
	  public long getPendingAdCount(){
		  String approvalStatus = "Pending";
		  String query = "from Product where approvalStatus = :approvalStatus";
		  long pendingCount = 0;
		  List<Product> productsList = new ArrayList<>();
		  
		  try{
			  productsList = entityManager
					  			.createQuery(query)
					  			.setParameter("approvalStatus", approvalStatus)
					  			.getResultList();
			  if(productsList != null){
				  return pendingCount = productsList.size();
			  }
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return pendingCount;
	  }
	  
	  
	  /**
	   * Return the Approved ads List
	   */

	  
	  public List<Product> getApprovedProductsList(){
		  String approvalStatus = "approved";
		  String query = "from Product where approvalStatus = :approvalStatus";
		  
		  List<Product> productsList = new ArrayList<>();
		  
		  try{
			  productsList = entityManager
					  			.createQuery(query)
					  			.setParameter("approvalStatus", approvalStatus)
					  			.getResultList();
			  if(productsList != null){
				  return productsList;
			  }
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return productsList;
	  }
	  
	  public List<Product> getApprovedProductsList2(){
		  String approvalStatus = "approved";
		  String query = "from Product where approvalStatus = :approvalStatus";
		  
		  List<Product> productsList = new ArrayList<>();
		  
		  try{
			  productsList = entityManager
					  			.createQuery(query)
					  			.setParameter("approvalStatus", approvalStatus)
					  			.getResultList();
			  if(productsList != null){
				  return productsList;
			  }
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

	  
	  /**
	   * Return the Approved ads under selected sub cat List
	   */

	  
	  public List<Product> getApprovedProductsListUnderSubCat(String subCategoryName){
		  String approvalStatus = "approved";
		  String query = "from Product where approvalStatus = :approvalStatus and subCategoryName = :subCategoryName";
		  
		  List<Product> productsList = new ArrayList<>();
		  
		  try{
			  productsList = entityManager
					  			.createQuery(query)
					  			.setParameter("approvalStatus", approvalStatus)
					  			.setParameter("subCategoryName", subCategoryName)
					  			.getResultList();
			  if(productsList != null){
				  return productsList;
			  }
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return productsList;
	  }
}
