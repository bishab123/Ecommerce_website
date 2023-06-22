package com.work.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity

public class OrderStatus {
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	private Product product;

	@ManyToOne
	private UserInfo userinfo;

	private String orderstatus;

}
