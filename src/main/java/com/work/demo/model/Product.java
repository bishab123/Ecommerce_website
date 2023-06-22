package com.work.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table

public class Product {

	@Id
	@GeneratedValue

	private int id;
	private String name;
	private int price;
	private String discription;
	private String image;

	@ManyToOne
	@JoinColumn
	private ProductCatagory productcatagory;

	@ManyToMany(mappedBy = "cartitem", fetch = FetchType.EAGER)
	private List<UserInfo> linkeduser;

	@ManyToOne
	private MyShop shop;

	@ManyToMany(mappedBy = "orderplaced", fetch = FetchType.EAGER)
	private List<UserInfo> addeduser;

	@ManyToMany(mappedBy = "confirmedorder", fetch = FetchType.EAGER)
	private List<UserInfo> addedusers;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<UserInteraction> userinteraction;

	private int popularitycount;
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductReview> productreviews;

}
