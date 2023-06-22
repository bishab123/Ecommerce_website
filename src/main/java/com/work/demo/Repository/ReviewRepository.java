package com.work.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.demo.model.ShopReview;

public interface ReviewRepository extends JpaRepository<ShopReview, Integer> {

	
	
}
