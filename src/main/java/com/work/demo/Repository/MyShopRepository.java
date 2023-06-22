package com.work.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.demo.model.MyShop;

public interface MyShopRepository extends JpaRepository<MyShop, Integer > {
   MyShop findByShopusernameAndShopcode(String un, String code);


}


