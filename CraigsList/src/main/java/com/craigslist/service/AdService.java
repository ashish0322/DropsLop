/**
 * 
 */
package com.craigslist.service;

import java.util.List;

import com.craigslist.model.Product;

/**
 * @author amaheedhara
 *
 */
public interface AdService {

	public String submitAd(Product product, String userName);
	
	public List<Product> getProducts();
	
	public Product approveAd(String action,long id);
	
	public Product getAd(long id);
	
	public String removeAd(long id);
	
	public List<Product> getUserPostings(String userName);
	
}
