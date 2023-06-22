package com.work.demo.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table

public class MyShop {
	@Id
	@GeneratedValue
	private int id;
	private String shopname;
	private String shopusername;
	private String shoppassword;
	private String moto;
	private String shopemail;
	private String shoplogo;

	private String shopcode;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@OneToOne(fetch = FetchType.LAZY)
	private UserInfo userinfo;

	@OneToMany(mappedBy = "shop",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Product> product;

	@OneToMany(mappedBy = "myShop", cascade = CascadeType.ALL)
	private List<Usercount> userCounts;
	
	
	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
     private List<ShopReview> review;

}
