/**
 * 
 */
package com.craigslist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author amaheedhara
 *
 */
@Entity
public class SiteStats {
	
	@Id 
	@GeneratedValue
	@Column(name="siteId", unique = true, nullable = false)
	private long siteId;
	
	private double pageViews;
	
	public SiteStats(){
		
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public double getPageViews() {
		return pageViews;
	}

	public void setPageViews(double pageViews) {
		this.pageViews = pageViews;
	}
	
	

}
