/**
 * 
 */
package com.craigslist.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craigslist.dao.ProductDao;
import com.craigslist.dao.UserDao;
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
	
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RequestMapping(value = "/api/user/getComments", method = RequestMethod.GET)
	public List<Comment> getComments(long productId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
