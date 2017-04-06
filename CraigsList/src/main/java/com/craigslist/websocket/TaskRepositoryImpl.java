/**
 * 
 */
package com.craigslist.websocket;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author amaheedhara
 *
 */
@Repository
@Scope("singleton")
public class TaskRepositoryImpl extends TaskRepository
{
	
	@Autowired 
	TaskRepositoryObserver observer;
	
	private List<Task> tasks = new LinkedList<>();
	
	@PostConstruct
	public void init() {
		this.addObserver(observer);
	}
	
	@Override
	public void add(Task task) {
		// TODO Auto-generated method stub
		synchronized (tasks) {
			System.out.println("Inside add task method TaskRepoImpl");
			this.tasks.add(task);
		}
		this.publish();
		
	}

	@Override
	public void remove(Task task) {
		// TODO Auto-generated method stub
		synchronized (tasks) {
			this.tasks.remove(task);
		}
		this.publish();
	}

	@Override
	public void forEach(Consumer<Task> typeConsumer) {
		// TODO Auto-generated method stub
		synchronized (tasks) {
			this.tasks.forEach(typeConsumer);
		}
	}

	@Override
	public List<Task> getAll() {
		return new LinkedList<>(this.tasks);
	}

}
