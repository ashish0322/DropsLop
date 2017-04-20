/**
 * 
 */
package com.craigslist.serviceImpl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craigslist.dao.SiteStatsDao;
import com.craigslist.dao.UserDao;
import com.craigslist.model.Session;
import com.craigslist.model.User;
import com.craigslist.model.UserInfo;
import com.craigslist.service.UserService;
import com.craigslist.util.ObjectFactory;
import com.craigslist.util.PasswordEncrypt;

/**
 * Class UserService
 */
@RestController
public class UserServciceImpl implements UserService{
	
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
	  
  // Wire the UserDao used inside this controller.
	  @Autowired
	  private UserDao userDao;
	  
	  @Autowired
	  private SiteStatsDao siteStatsDao;
	  
	  ObjectFactory objectFactory = new ObjectFactory();


  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create a new user with an auto-generated id and email and name as passed 
   * values.
   */
  @Override
  @RequestMapping(value="/api/signup",method = RequestMethod.POST,
			consumes = "application/json")
  public String signUp(@RequestBody User user) 
  	{
	  System.out.println("FirstName is"+user.getFirstName());
    try {
    	
    	// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String stringDate = df.format(today);
	  // encrypting password using secure hash algorithm
      String encryptedPass = PasswordEncrypt.getSHA1(user.getPassword());
      String role = "user";
      User newUser = new User(user.getFirstName(), user.getLastName(), user.getDisplayName(), user.getEmail(), encryptedPass);
      UserInfo userInfo = new UserInfo();
      userInfo.setRole(role);
      userInfo.setRegisteredDate(stringDate);
      newUser.setUserInfo(userInfo);
      userDao.create(newUser);
    }
    catch (Exception ex) {
      return JSONObject.quote("Error creating the user: " + ex.toString());
    }
    return JSONObject.quote("Registration Successfull!");
  }
  
  /**
   * 
   * Login User with provided email id and Password
   */
  @RequestMapping(value="/api/login",method = RequestMethod.POST,
			consumes = "application/json")
	public User login(@RequestBody User user,HttpServletRequest request) 
		{
		  System.out.println("FirstName is"+user.getEmail());
		  User validUser = new User();
		  User updatedValidUser;
	  try {
		  
		  String encryptedPass = PasswordEncrypt.getSHA1(user.getPassword());
		  validUser = userDao.getByEmailAndPassword(user.getEmail(),encryptedPass);
		  updatedValidUser = updateLastLoginTime(validUser);
		  Session userSession =  objectFactory.createSession();
		  userSession.setUser(updatedValidUser);
		  userSession.setAccessIpAddress(request.getRemoteAddr());
		  request.getSession().setAttribute(REST_API_SESSION_KEY, userSession);
	  }
	  catch (Exception ex) {
	    return null;
	  }
	  return updatedValidUser;
	}
  
  /**
   * Delete the user with the passed id.
   */
  @Override
  @RequestMapping(value="/delete")
  public String delete(long id) {
    try {
      User user = new User(id);
      userDao.delete(user);
    }
    catch (Exception ex) {
      return JSONObject.quote("Error deleting the user: " + ex.toString());
    }
    return JSONObject.quote("User succesfully deleted");
  }
  
  
  /**
   * Retrieve the id for the user with the passed email address.
   */
  @Override
  @RequestMapping(value="/api/validateEmail/{email_id:.+}",method = RequestMethod.GET)
  public String getByEmail(@PathVariable("email_id") String email) {
	  
    try {
    	userDao.getByEmail(email);
    }
    catch (Exception ex) {
    	 return null;
    }
    return JSONObject.quote("Email already Registered");
   
  }
  
  
  
  /**
   * Update the email and the name for the user indentified by the passed id.
   */
  @Override
  @RequestMapping(value="/update")
  public String updateName(long id, String email, String name) {
    try {
      User user = userDao.getById(id);
      user.setEmail(email);
      user.setFirstName(name);
      userDao.update(user);
    }
    catch (Exception ex) {
      return JSONObject.quote("Error updating the user: " + ex.toString());
    }
    return JSONObject.quote("User succesfully updated");
  }
  
  //Logout API 

	@Override
	@RequestMapping(value="/api/logout")
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println("Inside logout api");
		if(user != null){
			session.invalidate();
		}
	} 
	
	//Timeout API
	
	@Override
	@RequestMapping(value = "/api/timeout", method = RequestMethod.GET)
	public void doTimeout(HttpServletRequest req) {
	
			Session session = getCurrentSession(req);
			if (session != null) {
				
				
			}
			req.getSession().removeAttribute(REST_API_SESSION_KEY);
		}
	
	
	@Override
	@RequestMapping(value = "/api/admin/getUsers", method = RequestMethod.GET)
	public List<User> getUsers() {
		
		List<User> usersList = new ArrayList<>();
				
		usersList = userDao.getUsersList();
				if(!usersList.isEmpty()){
					return usersList;
				}
				else{
					return null;
				}
	
	}
	
	@Override
	@RequestMapping(value = "/api/admin/{action}/lockUnlockUser/{id}", method = RequestMethod.GET)
	public User lockUnlockUser(@PathVariable String action,@PathVariable long id) {
		// TODO Auto-generated method stub
		User user = null;
		 try {
		      user = userDao.getById(id);
		      if(action.equalsIgnoreCase("true")){
		 		 user.setActive(true);
		      }
		 	  else{
		 		 user.setActive(false);
		 	  }
		      user = userDao.update(user);
		 }
		 catch (Exception ex) {
		      return user;
		    }
		
		 
		return user;
	}
	
	
	@Override
	@RequestMapping(value = "/api/admin/getUserCount", method = RequestMethod.GET)
	public long getUsersCount() {
		
		long usersCount = 0;
		
		usersCount = userDao.getUsersCount();
		
		return usersCount;
	}
	
	@Override
	@RequestMapping(value = "/api/admin/getBlockedUserCount", method = RequestMethod.GET)
	public long getBlockedUsersCount() {
		
		long blockedUsersCount = 0;
		
		blockedUsersCount = userDao.getBlockedUsersCount();
		
		return blockedUsersCount;
	}
	
	@Override
	@RequestMapping(value = "/api/admin/getPageViewsCount", method = RequestMethod.GET)
	public double getPageviewsCount() {
		
		double pageViews = 0;
		
		pageViews = siteStatsDao.getPageViews();
		
		return pageViews;
	}
	// methods

	public static Session getCurrentSession(HttpServletRequest request) {

				if (request != null) {
					
					HttpSession httpSession = request.getSession();
					if (httpSession != null) {
						Object o = httpSession.getAttribute(UserService.REST_API_SESSION_KEY);
						if (o != null && o instanceof Session) {
							Session session = ((Session) o);
							return session;
						}
					}
				}
			return null;
		}

	// 
	public User updateLastLoginTime(User user){
		
		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String lastLoginDate = df.format(today);
		
		UserInfo userInfo = user.getUserInfo();
		userInfo.setLastLoginDate(lastLoginDate);
		user.setUserInfo(userInfo);
		return userDao.update(user);
		
	}

	

	

	

  
} 