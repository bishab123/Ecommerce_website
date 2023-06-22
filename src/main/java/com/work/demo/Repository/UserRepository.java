package com.work.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.demo.model.Product;
import com.work.demo.model.UserInfo;


public interface UserRepository extends JpaRepository<UserInfo, Integer>{

	
	UserInfo findByUsernameAndPassword(String un, String pw);
	
	
	List<UserInfo> findByCartitemContaining(Product p);
}
