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
import com.craigslist.model.SiteStats;
import com.craigslist.model.User;

/**
 * @author amaheedhara
 *
 */
@Repository
@Transactional
public class SiteStatsDao {
	

	 // An EntityManager will be automatically injected from entityManagerFactory
	  // setup on DatabaseConfig class.
	  @PersistenceContext
	  private EntityManager entityManager;
	  
	  
	  /**
	   * Save the stat in the database.
	   */
	  public void create(SiteStats stat) {
	    entityManager.persist(stat);
	    return;
	  }
	  
	  
	  /**
	   * Return the stats
	   */
	  @SuppressWarnings("unchecked")
	  public List<SiteStats> getAll() {
	    return entityManager.createQuery("from SiteStats").getResultList();
	  }
	  /**
	   * Update the stat  in the database.
	   */
	  public SiteStats update(SiteStats stat) {
		  return entityManager.merge(stat);
	   
	  }


	  
	  /**
	   * Return pageView stat
	   */
	  
	  public double getPageViews() {
		  
		  double pageViews = 0;
		  	List<SiteStats> stats = new ArrayList<>();
		  	boolean active = false;
		  	
		  	stats = entityManager.createQuery("from SiteStats").getResultList();
		  	if(stats != null){
		  		for(SiteStats stat : stats){
		  			pageViews =  stat.getPageViews();
		  		}
		  		
		  		return pageViews;
		  	}
		    return pageViews;
		  }
}
