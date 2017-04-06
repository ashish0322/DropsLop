/**
 * 
 */
package com.craigslist.websocket;

import javax.websocket.CloseReason;
import javax.websocket.Session;

/**
 * @author amaheedhara
 *
 */
public interface WebSocketHandler {
	public void onOpen(Session userSession);
	public void onClose(CloseReason closeReason, Session userSession);
}