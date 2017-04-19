/**
 * 
 */
package com.craigslist.service;

import java.util.List;

import com.craigslist.websocket.Comment;

/**
 * @author amaheedhara
 *
 */
public interface CommentsService {
	
		public String addComment(Comment comment);
		
		public List<Comment> getComments(long productId);

}
