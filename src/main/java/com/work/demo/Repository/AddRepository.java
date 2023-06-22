package com.work.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.demo.model.Product;

public interface AddRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByProductcatagory(String cata);
	
	List<Product> findByNameContaining(String name);
	
}
