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
public class CommentBroadcaster implements Broadcaster<Comment>{
	
	@Autowired
	private ClientRepository clients;
	private Gson gson;
	
	@PostConstruct
	public void init(){
		this.gson = new Gson();
	}
	
	@Override
	public void broadcast(List<Comment> task) {
		// TODO Auto-generated method stub
		
		System.out.println("Inside broadcast method of CommentBroadcaster");
		
		System.out.println("broadcast method is called on CommentBroadcaster from CommentRepository Observer with taskrepo as input");
		this.clients.forEach(client -> {
			
			try{
				client.sendText(this.gson.toJson(task));
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	
}
