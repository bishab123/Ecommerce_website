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
@Table(name = "user_auth")
public class UserInfo {
	@Id
	@GeneratedValue
	private int id;

	private String fname;
	private String lname;
	private String username;
	private String password;
	private String email;
	private String address;
	private String number;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Product> cartitem;

	@OneToOne(mappedBy = "userinfo", cascade = CascadeType.ALL)
	private MyShop shop;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Product> orderplaced;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Product> confirmedorder;

	@OneToMany(mappedBy = "userInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Usercount> userCounts;

	@OneToMany(mappedBy = "userinfos", cascade = CascadeType.ALL)
	private List<UserInteraction> userinteraction;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ShopReview> review;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ProductReview> preview;

}
