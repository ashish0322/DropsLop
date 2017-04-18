/**
 * 
 */
package com.craigslist.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author amaheedhara
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private CommentWebSocketHandler taskWebSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		try
		{
		registry.addHandler(taskWebSocketHandler, "/websocket");
		System.out.println(" adding Socket handler :::::::::: ");
		}
		catch(Exception e)
		{
		System.out.println("Exception while adding Socket handler :::::::::: ");
		}
		
	}
	
}
