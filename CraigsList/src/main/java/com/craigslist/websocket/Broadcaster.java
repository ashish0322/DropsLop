/**
 * 
 */
package com.craigslist.websocket;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author amaheedhara
 *
 */
@Component
public interface Broadcaster<T> {
	public void broadcast(List<T> objToBroadCast);

}

