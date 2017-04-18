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
	private String commentId;
	  
	private String userId;
	private String commenterName;
	  
	private String productId;
	  
	private String description;
	  
	private String commentedDate;
	
	@ManyToOne
    @JoinColumn(name="product")
	@JsonBackReference(value="mappedToProd")
    private Product product;
	
	public Comment(String userId,String productId,String description,String commentedDate,String commenterName){
		this.userId = userId;
		this.productId = productId;
		this.description = description;
		this.commentedDate = commentedDate;
		this.commenterName = commenterName;
	}
	  
	  
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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


	public String getCommentId() {
		return commentId;
	}


	public void setCommentId(String commentId) {
		this.commentId = commentId;
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
