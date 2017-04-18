/**
 * 
 */
package com.craigslist.websocket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.craigslist.model.Product;

/**
 * @author amaheedhara
 *
 */
@Repository
@Scope("singleton")
public class CommentRepositoryImpl extends CommentRepository
{
	
	@Autowired 
	CommentRepositoryObserver observer;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	 List<Comment> commentsList = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		this.addObserver(observer);
	}
	
	@Override
	public void add(Comment comment) {
		entityManager.persist(comment);
		this.publish();
		
	}

	@Override
	public void remove(Comment task) {
		
	}

	@Override
	public void forEach(Consumer<Comment> typeConsumer) {
		synchronized (commentsList) {
			this.commentsList.forEach(typeConsumer);
		}
	}

	@Override
	public List<Comment> getAllById(long productId) {

		 String query = "from Comment where productId = :productId";
		  
		  try{
			  commentsList = entityManager
					  			.createQuery(query)
					  			.setParameter("productId", productId)
					  			.getResultList();
		  }
		  catch (Exception e) {
			e.printStackTrace();
		}
		  return commentsList;
	}

	@Override
	public List<Comment> getAll() {
		// TODO Auto-generated method stub
		return new LinkedList<>(this.commentsList);
	}

}
