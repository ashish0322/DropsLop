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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author amaheedhara
 *
 */
@Entity
@Table(name="subCategory")
public class SubCategory {
	
	@Id 
	@GeneratedValue
	@Column(name="subCategoryId", unique = true, nullable = false)
	private long subCategoryId;
	
	@Column(nullable = false)
	private String name;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="subCategory",targetEntity=Product.class,
			cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Product> products = new HashSet<Product>();
	
	@ManyToOne
    @JoinColumn(name="category")
	@JsonBackReference
    private Category category;
	
	public SubCategory(){
		
	}
	
	public SubCategory(String name,Category category){
		this.name = name;
		this.category = category;
		this.products = new HashSet<Product>();
	}

	public long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
        getProducts().add(product);
    }

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

}
