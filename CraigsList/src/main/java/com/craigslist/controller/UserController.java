/**
 * 
 */
package com.craigslist.controller;

import javax.servlet.http.HttpServletRequest;

import com.craigslist.model.User;

/**
 * @author ashish
 *
 */
public interface UserController {
	
	public static final String REST_API_SESSION_KEY = "DROPSLOP_API_SESSION_KEY";

	public String signUp(User user);
	public User login(User user,HttpServletRequest request);
	public void logout(HttpServletRequest request);
	public String delete(long id);
	public String getByEmail(String email);
	public String updateName(long id, String email, String name);
	
	public void doTimeout(HttpServletRequest req);
}
