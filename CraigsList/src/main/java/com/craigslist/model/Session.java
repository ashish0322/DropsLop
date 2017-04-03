/**
 * 
 */
package com.craigslist.model;

/**
 * @author amaheedhara
 *
 */
public class Session {
	
	private User user;
	protected String accessIpAddress;

	public String getAccessIpAddress() {
		return accessIpAddress;
	}

	public void setAccessIpAddress(String accessIpAddress) {
		this.accessIpAddress = accessIpAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
