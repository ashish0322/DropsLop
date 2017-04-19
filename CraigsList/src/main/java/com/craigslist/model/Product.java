/**
 * 
 */
package com.craigslist.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.craigslist.websocket.Comment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author amaheedhara
 *
 */


@Entity
@Table(name="producttable")
public class Product {

	@Id 
	@GeneratedValue
	@Column(name="productId", unique = true, nullable = false)
    private long productId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private String description;
	
	private int spam;
	
	private int rating;
	
	private long views;
	
	private String purchasedYear;
	
	private String address;
	
	private String photoName; // Map with hibernate
	
	private String postedDate;
	
	private String approvalStatus;
	
	@Transient
    private MultipartFile photo; // Use by Data Binder

    @Transient
    private Category category;
    
	@Column(nullable = false)
    private String sellerName;
    
	@ManyToOne
    @JoinColumn(name="user")
	@JsonBackReference(value="mappedToProduct")
    private User user;
	
	private double latitude;
	private double longitude;
	
	private String sellerEmail;
	private String contact;
	
    private String categoryName;
    
    
    private String subCategoryName;
    
    @ManyToOne
    @JoinColumn(name="subCategoryId")
    @JsonBackReference(value="mappedToSubCat")
    private SubCategory subCategory;
    
    @OneToMany(fetch=FetchType.LAZY,mappedBy="product",targetEntity=Comment.class,
			cascade = CascadeType.ALL)
	@JsonManagedReference(value="mappedToProd")
	private Set<Comment> comments = new HashSet<Comment>();
    

    public Product(String title, String purchasedYear, String address,String productName,String categoryName,
    		double price,String description,String photoName,String subCategoryName,String sellerEmail,
    		String sellerName,User user,SubCategory subCategory,String approvalStatus,String postedDate,
    		String contact,double latitude,double longitude) {
        this.productName = productName;
        this.sellerName = sellerName;
        this.categoryName=categoryName;
        this.price = price;
        this.description = description;
        this.photoName= photoName;
        this.subCategoryName = subCategoryName;
        this.title = title;
        this.purchasedYear = purchasedYear;
        this.address = address;
        this.sellerEmail = sellerEmail;
        this.user = user;
        this.subCategory = subCategory;
        this.approvalStatus = approvalStatus;
        this.postedDate = postedDate;
        this.contact = contact;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Product() {
    }

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	


	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}


	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	
	public long getViews() {
			return views;
		}

	public void setViews(long views) {
		this.views = views;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPurchasedYear() {
		return purchasedYear;
	}

	public void setPurchasedYear(String purchasedYear) {
		this.purchasedYear = purchasedYear;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getSpam() {
		return spam;
	}

	public void setSpam(int spam) {
		this.spam = spam;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public void addComment(Comment comment) {
		getComments().add(comment);
    }
	
}
