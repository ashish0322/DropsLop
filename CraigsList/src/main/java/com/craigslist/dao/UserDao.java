/**
 * 
 */
package com.craigslist.dao;

import java.util.ArrayList;
/**
 * @author ashish
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.craigslist.model.Category;
import com.craigslist.model.Product;
import com.craigslist.model.User;

/**
 * This class is used to access data for the User entity.
 * Repository annotation allows the component scanning support to find and 
 * configure the DAO wihtout any XML configuration and also provide the Spring 
 * exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class UserDao {
	
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * Save the user in the database.
   */
  public void create(User user) {
    entityManager.persist(user);
    return;
  }
  
  /**
   * Delete the user from the database.
   */
  public void delete(User user) {
    if (entityManager.contains(user))
      entityManager.remove(user);
    else
      entityManager.remove(entityManager.merge(user));
    return;
  }
  
  /**
   * Return all the users stored in the database.
   */
  @SuppressWarnings("unchecked")
  public List<User> getAll() {
    return entityManager.createQuery("from User").getResultList();
  }
  
  
  /**
   * Return user count
   */
  
  public long getUsersCount() {
	  
	  	long userCount = 0;
	  	List<User> userList = new ArrayList<>();
	  	
	  	
	  	userList = entityManager.createQuery("from User").getResultList();
	  	if(userList != null){
	  		return userList.size();
	  	}
	    return userCount;
	  }
  
  /**
   * Return blocked user count
   */
  
  public long getBlockedUsersCount() {
	  
	  	long userCount = 0;
	  	List<User> userList = new ArrayList<>();
	  	boolean active = false;
	  	
	  	userList = entityManager.createQuery("from User where active = :active")
	  			.setParameter("active", active)
	  			.getResultList();
	  	if(userList != null){
	  		return userList.size();
	  	}
	    return userCount;
	  }
  /**
   * Return the user having the passed email.
   */
  public User getByEmail(String email) {
    return (User) entityManager.createQuery(
        "from User where email = :email")
        .setParameter("email", email)
        .getSingleResult();
  }
  
  /**
   * Return the user having the passed email and password.
   */
  public User getByEmailAndPassword(String email,String password) {
    return (User) entityManager.createQuery(
        "from User where email = :email and password = :password")
        .setParameter("email", email)
        .setParameter("password", password)
        .getSingleResult();
  }

  /**
   * Return the user having the passed id.
   */
  public User getById(long id) {
    return entityManager.find(User.class, id);
  }

  /**
   * Update the passed user in the database.
   */
  public User update(User user) {
	  return entityManager.merge(user);
   
  }

  
//get list of users

	  public List<User> getUsersList(){
		  
		  List<User> usersList = new ArrayList<>();
		  String query = "select u from User u";
		  try{
			  usersList = entityManager.createQuery(query).getResultList();
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return usersList;
	  }
 
  
}
