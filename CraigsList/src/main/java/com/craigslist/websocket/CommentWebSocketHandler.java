/**
 * 
 */
package com.craigslist.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 * @author amaheedhara
 *
 */
@Component
public class CommentWebSocketHandler extends TextWebSocketHandler{
	
	private static Logger _logger = Logger.getLogger(CommentWebSocketHandler.class);
	
	@Autowired 
	private ClientRepository clientRepository;

	@OnOpen
	public void onOpen(Session session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" CommentWebSocketHandler &&&&&&&&&&&&&&&7  On Open Session **********************");
		
	}

	@OnClose
	public void onClose(Session session) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" CommentWebSocketHandler &&&&&&&&&&&&&&&7  After Connection closed **********************");
		
		_logger.info("Closed %s "+session.getId());
		try {
			this.clientRepository.remove(new Client(session));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Not able to close the connection ::::::::::::::: ");
		}
		
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println(" CommentWebSocketHandler &&&&&&&&&&&&&&&7  After connection Established **********************");
		System.out.println("Connected "+session.getId());
		try {
			this.clientRepository.add(new Client(session));
		} catch (Exception e) {
			System.out.println("Not able to open the connection ::::::::::::::: ");
		}
	}
	
}
