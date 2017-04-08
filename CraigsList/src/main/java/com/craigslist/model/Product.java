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
	private String productName;
	@Column(nullable = false)
    private String company;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private int rating;
	
	@Column(nullable = false)
	private long views;
	
	@Column(nullable = false)
	private String photoName; // Map with hibernate
	
	@Transient
    private MultipartFile photo; // Use by Data Binder
		
    @ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="seller")
    private User user;


	@Column(nullable = false)
    private String sellerName;
    
    @Column(nullable = false)
    private String category_name;
    
    @ManyToOne
    @JoinColumn(name="subCategoryId")
    private SubCategory subCategory;
    
    @OneToMany(fetch=FetchType.EAGER,mappedBy="product",targetEntity=Comment.class,
			cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>();

    public Product(String productName, String company, User user,String categoryName,
    		double price,String description,int rating,String photoName,SubCategory subCategory,
    		String sellerName) {
        this.productName = productName;
        this.company = company;
        this.user = user;
        this.sellerName = sellerName;
        this.category_name=categoryName;
        this.price = price;
        this.description = description;
        this.rating= rating;
        this.photoName= photoName;
        this.subCategory = subCategory;
        this.comments = new HashSet<Comment>();
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}


	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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
	
	
	public long getViews() {
			return views;
		}

	public void setViews(long views) {
		this.views = views;
	}
}
