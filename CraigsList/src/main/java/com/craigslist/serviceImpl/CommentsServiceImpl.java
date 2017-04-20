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

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craigslist.dao.ProductDao;
import com.craigslist.dao.UserDao;
import com.craigslist.model.Category;
import com.craigslist.service.CommentsService;
import com.craigslist.websocket.Comment;
import com.craigslist.websocket.CommentRepositoryImpl;

/**
 * @author amaheedhara
 *
 */
@RestController
public class CommentsServiceImpl implements CommentsService{
	
	@Autowired
	private UserDao userDao;
	  
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private CommentRepositoryImpl commentsDao;

	@Override
	@RequestMapping(value="/api/user/postComment",method = RequestMethod.POST,
	consumes = "application/json")
	public String addComment(@RequestBody Comment comment) {
		
				// Create an instance of SimpleDateFormat used for formatting 
				// the string representation of date (month/day/year)
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			
				// Get the date today using Calendar object.
				Date today = Calendar.getInstance().getTime();        
				// Using DateFormat format method we can create a string 
				// representation of a date with the defined format.
				String stringDate = df.format(today);
		
		
		    try {
		      Comment newComment = new Comment(comment.getUserId(), comment.getProductId(), comment.getDescription(), stringDate, comment.getCommenterName());
		      commentsDao.getAllById(comment.getProductId());
		      commentsDao.add(newComment);
		      
		    }
		    catch (Exception ex) {
		      return JSONObject.quote("Error adding comment: " + ex.toString());
		    }
		    return JSONObject.quote("Comment added Successfully");
		
	}

	@Override
	@RequestMapping(value = "/api/user/getComments/{productId}", method = RequestMethod.GET)
	public List<Comment> getComments(@PathVariable long productId) {
		
		List<Comment> commentsList = new ArrayList<>();
				
		commentsList = commentsDao.getAllById(productId);
				if(!commentsList.isEmpty()){
					return commentsList;
				}
				else{
					return null;
				}
		
	}
	
	
	

}
