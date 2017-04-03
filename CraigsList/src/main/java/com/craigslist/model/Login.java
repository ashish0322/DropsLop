/**
 * 
 */
package com.craigslist.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


/**
 * @author ashish
 *
 */
@Entity
public class Login {

	@Id
	@GeneratedValue
	@Column(name="login_pk", unique = true, nullable = false)
	long id;

	@NotNull
	String userName;
	
	@NotNull
	String password;
	
	@NotNull
	String role;
	
	@OneToOne(mappedBy = "login",cascade = CascadeType.ALL)
	private User user;
	
	
	 public Login(String userName,String password,String role) { 
		   this.userName = userName;
		   this.password = password;
		   this.role = role;
		}
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
