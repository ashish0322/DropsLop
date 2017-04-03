/**
 * 
 */
package com.craigslist.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * @author ashish
 *
 */
@Entity
@Table(name="user")
public class User {

	// ------------------------
    // PRIVATE FIELDS
    // ------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String displayName;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="login_fk")
	private Login login;
	
	// ------------------------
	// PUBLIC METHODS
	// ------------------------
	  
	public User() { }
	
	public User(long userId) {
		this.userId = userId;
	}

    public User(String firstName,String lastName,String displayName,String email,
    		String password) { 
	   this.firstName = firstName;
	   this.lastName = lastName;
	   this.displayName= displayName;
	   this.email = email;
	   this.password = password;
	}

	public long getId() {
		return userId;
	}

	public void setId(long id) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
