/**
 * 
 */
package com.craigslist.websocket;

import java.util.List;
import java.util.Observable;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 * @author amaheedhara
 *
 */
@Repository
@Transactional
public abstract class CommentAppRespository<K, T> extends Observable {
	  public abstract void add(T type);
	  public abstract void remove(T type);
	  public abstract void forEach(Consumer<T> typeConsumer);
	  public abstract List<T> getAllById(long id);
	  public abstract List<T> getAll();

	  protected void publish() {
	    this.setChanged();
	    this.notifyObservers();
	  }
	}