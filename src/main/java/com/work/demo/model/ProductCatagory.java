package com.work.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product_catagory")
public class ProductCatagory {

	@Id
	@GeneratedValue
	private int id;
	private String catagoryname;
	@OneToMany(mappedBy = "productcatagory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Product> product;

}
