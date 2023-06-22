package com.work.demo.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.work.demo.Repository.MyShopRepository;
import com.work.demo.Service.ShopService;
import com.work.demo.model.MyShop;

@Component
public class ShopServiceImpl implements ShopService {

	@Autowired
	private MyShopRepository msr;

	@Override
	public void createShop(MyShop user) {
		msr.save(user);

	}

	@Override
	public MyShop getShop(int id) {
		MyShop shop = msr.getById(id);
		return shop;
	}

	

}
