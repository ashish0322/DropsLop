/**
 * 
 */
package com.craigslist.websocket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author amaheedhara
 *
 */
@Repository
public abstract class CommentRepository extends CommentAppRespository<String, Comment>{
	
	@Autowired 
	private BeanFactory factory;
	
	@PostConstruct
	public void init(){
		this.addObserver(this.factory.getBean(CommentRepositoryObserver.class));
		System.out.println("after obeserver method invoked method in TaskRepo which notifies CommentRepositoryObserver");
	}
}
