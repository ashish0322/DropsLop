/**
 * 
 */
package com.craigslist.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private int quantity;
	
	
	@Column(nullable = false)
	private String photoName; // Map with hibernate
	
	@Transient
    private MultipartFile photo; // Use by Data Binder
		
    @ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="employee")
    private Seller seller;
    
    @Column(nullable = false)
    private String sellerName;
    
    @Column(nullable = false)
    private String category_name;
    
    @ManyToOne
    @JoinColumn(name="categoryid")
    private Category category;

    public Product(String productName, String company, Seller seller,String categoryName,
    		double price,String description,int quantity,String photoName,Category category,
    		String sellerName) {
        this.productName = productName;
        this.company = company;
        this.seller = seller;
        this.sellerName = sellerName;
        this.category_name=categoryName;
        this.price = price;
        this.description = description;
        this.quantity= quantity;
        this.photoName= photoName;
        this.category = category;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public Seller getEmployee() {
		return seller;
	}

	public void setEmployee(Seller seller) {
		this.seller = seller;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	
	

          

   
	
}
