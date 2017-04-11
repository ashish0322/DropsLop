/**
 * 
 */
package com.craigslist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	@Column(name="commentid", unique = true, nullable = false)
	private long commentid;
	
	@Column(nullable = false)
	private String comment;
	
	@ManyToOne
    @JoinColumn(name="productId")
	@JsonBackReference
    private Product product;
	
	
	public Comment(){
		
	}
	
	public Comment(String comment,Product product){
		
		this.comment = comment;
		this.product = product;
		
	}

	public long getCommentid() {
		return commentid;
	}

	public void setCommentid(long commentid) {
		this.commentid = commentid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	
}
