package com.work.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class ProductReview {

	
	@Id
	@GeneratedValue
	public int id;
	
	private int stars;
	
	private String message;
	
	@ManyToOne
	private UserInfo user;
	
	@ManyToOne
	private Product product;
	

	
	
	
}
