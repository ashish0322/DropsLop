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
public class TaskRepositoryObserver implements Observer{
	
	@Autowired
	private TaskBroadcaster broadcaster;
	
	@Override
	public void update(Observable repository, Object param) {
		// TODO Auto-generated method stub
		System.out.println("Inside update method of TaskRepositoryObserver After publish method TaskRepoImpl");
		TaskRepository repo = (TaskRepository) repository;
		this.broadcaster.broadcast(repo.getAll());

	}
	
}
