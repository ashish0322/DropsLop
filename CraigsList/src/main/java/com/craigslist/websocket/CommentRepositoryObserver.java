/**
 * 
 */
package com.craigslist.websocket;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author amaheedhara
 *
 */
@Component
public class CommentRepositoryObserver implements Observer{
	
	@Autowired
	private CommentBroadcaster broadcaster;
	
	@Override
	public void update(Observable repository, Object param) {
		// TODO Auto-generated method stub
		System.out.println("Inside update method of CommentRepositoryObserver After publish method TaskRepoImpl");
		CommentRepository repo = (CommentRepository) repository;
		this.broadcaster.broadcast(repo.getAll());

	}
	
}
