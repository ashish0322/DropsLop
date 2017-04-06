/**
 * 
 */
package com.craigslist.websocket;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;


/**
 * @author amaheedhara
 *
 */
@Component
public class TaskBroadcaster implements Broadcaster<Task>{
	
	@Autowired
	private ClientRepository clients;
	private Gson gson;
	
	@PostConstruct
	public void init(){
		this.gson = new Gson();
	}
	
	@Override
	public void broadcast(List<Task> task) {
		// TODO Auto-generated method stub
		
		System.out.println("Inside broadcast method of TaskBroadcaster");
		
		System.out.println("broadcast method is called on TaskBroadcaster from TaskRepository Observer with taskrepo as input");
		this.clients.forEach(client -> {
			try{
				client.sendText(this.gson.toJson(task));
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	
}
