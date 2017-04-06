/**
 * 
 */
package com.craigslist.websocket;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author amaheedhara
 *
 */
@Repository
@Scope("singleton")
public class ClientRepositoryImpl extends ClientRepository {
	private List<Client> clients = new LinkedList<>();
	
	@Override
	public void add(Client session) {
		synchronized (this.clients) {
			this.clients.add(session);
		}
	}
	
	@Override
	public void remove(Client session) {
		synchronized (this.clients) {
			this.clients.remove(session);
		}
	}
	
	@Override
	public void forEach(Consumer<Client> clientConsume) {
		synchronized (this.clients) {
			this.clients.forEach(clientConsume);
		}
	}

	@Override
	public List<Client> getAll() {
		return new LinkedList<>(this.clients);
	}

}
