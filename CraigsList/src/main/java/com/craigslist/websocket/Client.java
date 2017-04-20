/**
 * 
 */
package com.craigslist.websocket;

import java.io.IOException;

import javax.websocket.Session;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author amaheedhara
 *
 */
//Client is nothing but the browser
public class Client {
	private final String id;
	private final WebSocketSession session;
	
	public Client(WebSocketSession session2){
		this.id = session2.getId();
		this.session = session2;
	}
	
	public void sendText(String text) throws IOException {
		this.session.sendMessage(new TextMessage(text));
	}
	
	public String getId() {
		 return id;
	}
}
