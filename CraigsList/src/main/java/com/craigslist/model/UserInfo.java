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


/**
 * @author amaheedhara
 *
 */
@Entity
public class UserInfo {

	
		@Id
		@GeneratedValue
		@Column(name="userInfo_pk", unique = true, nullable = false)
		private long userInofId;
		
		private String street;
		
		private String city;
		
		private String state;
		
		private String country;
	    
		private String contactNumber;
		
		private String pinCode;
		
		private String role;
		
		private String lastLoginDate;
		
		private long ratedBy;
		
		private int rating;

		@OneToOne(mappedBy = "userInfo",cascade = CascadeType.ALL)
		private User user;
		
		
		public UserInfo(){
			
		}


		public long getUserInofId() {
			return userInofId;
		}


		public void setUserInofId(long userInofId) {
			this.userInofId = userInofId;
		}


		public String getStreet() {
			return street;
		}


		public void setStreet(String street) {
			this.street = street;
		}


		public String getCity() {
			return city;
		}


		public void setCity(String city) {
			this.city = city;
		}


		public String getState() {
			return state;
		}


		public void setState(String state) {
			this.state = state;
		}


		public String getCountry() {
			return country;
		}


		public void setCountry(String country) {
			this.country = country;
		}


		public String getContactNumber() {
			return contactNumber;
		}


		public void setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
		}


		public String getPinCode() {
			return pinCode;
		}


		public void setPinCode(String pinCode) {
			this.pinCode = pinCode;
		}


		public String getRole() {
			return role;
		}


		public void setRole(String role) {
			this.role = role;
		}


		public String getLastLoginDate() {
			return lastLoginDate;
		}


		public void setLastLoginDate(String lastLoginDate) {
			this.lastLoginDate = lastLoginDate;
		}


		public long getRatedBy() {
			return ratedBy;
		}


		public void setRatedBy(long ratedBy) {
			this.ratedBy = ratedBy;
		}


		public int getRating() {
			return rating;
		}


		public void setRating(int rating) {
			this.rating = rating;
		}


		public User getUser() {
			return user;
		}


		public void setUser(User user) {
			this.user = user;
		}
		
		
}
