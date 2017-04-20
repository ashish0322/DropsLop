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
		System.out.println("Inside client remove");
		synchronized (this.clients) {
			for(Client client : this.clients){
				
					if(client.getId() == session.getId()){
							this.clients.remove(client);
					}
			}
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

	@Override
	public List<Client> getAllById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
