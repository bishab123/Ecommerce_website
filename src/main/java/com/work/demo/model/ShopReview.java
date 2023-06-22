package com.work.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity

public class ShopReview {

	@Id
	@GeneratedValue

	private int id;
	private String review;
	private int starcount;

	@ManyToOne(fetch = FetchType.LAZY)

	private UserInfo user;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	private MyShop shop;
}
