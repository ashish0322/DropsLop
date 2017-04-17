/**
 * 
 */
package com.craigslist.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.craigslist.model.Product;

/**
 * @author amaheedhara
 *
 */
public interface AdService {

	public String submitAd(String product, String userName,MultipartFile file,HttpServletRequest request);
	
	public List<Product> getProducts();
	
	public Product approveAd(String action,long id);
	
	public Product getAd(long id);
	
	public String removeAd(long id);
	
	public List<Product> getUserPostings(String userName);
	
	public long getPendingAds();
	
	public List<Product> getApprovedProducts();
	
	public Product getAdByUser(long id);
	
	public String removeAdByUser(long id);
	
	public String updateAd(Product product, long productId);
	
}
