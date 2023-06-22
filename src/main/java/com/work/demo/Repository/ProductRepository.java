package com.work.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.demo.model.Product;
import com.work.demo.model.ProductCatagory;

public interface ProductRepository  extends JpaRepository<ProductCatagory, Integer> {
	ProductCatagory findByCatagoryname(String cata);
}
