package com.work.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.demo.model.Product;

public interface RemoveRepository extends JpaRepository<Product, Integer> {

}
