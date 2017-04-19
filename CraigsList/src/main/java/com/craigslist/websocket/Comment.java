/**
 * 
 */
package com.craigslist.websocket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.craigslist.model.Category;
import com.craigslist.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author amaheedhara
 *
 */
@Entity
@Table(name="commentsTable")
public class Comment {
	
	
	@Id 
	@GeneratedValue
	@Column(name="commentId", unique = true, nullable = false)
	private long commentId;
	  
	private String userId;
	private String commenterName;
	  
	private long productId;
	  
	private String description;
	  
	private String commentedDate;
	
	@ManyToOne
    @JoinColumn(name="product")
	@JsonBackReference(value="mappedToProd")
    private Product product;
	
	public Comment(String userId,long productId,String description,String commentedDate,String commenterName){
		this.userId = userId;
		this.productId = productId;
		this.description = description;
		this.commentedDate = commentedDate;
		this.commenterName = commenterName;
	}
	  
	public Comment(){
		
	}
	  
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCommentedDate() {
		return commentedDate;
	}
	public void setCommentedDate(String commentedDate) {
		this.commentedDate = commentedDate;
	}

	public long getCommentId() {
		return commentId;
	}


	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}


	public long getProductId() {
		return productId;
	}


	public void setProductId(long productId) {
		this.productId = productId;
	}


	public String getCommenterName() {
		return commenterName;
	}


	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
	  
	
	  
	  
}
