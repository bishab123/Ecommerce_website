package com.work.demo.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.work.demo.Repository.AddRepository;
import com.work.demo.Repository.ProductRepository;
import com.work.demo.Repository.UserRepository;
import com.work.demo.Service.ProductService;
import com.work.demo.model.Product;
import com.work.demo.model.ProductCatagory;

import jakarta.transaction.Transactional;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productrepo;

	@Autowired
	private UserRepository ur;

	@Autowired
	private AddRepository addrepo;

	@Override
	public List<ProductCatagory> loadAllProducts() {
		List<ProductCatagory> pc = productrepo.findAll();
		return pc;
	}

	@Override
	public void addProducts(Product ps) {
		addrepo.save(ps);

	}

	@Override
	public List<Product> showAllProduct() {
		List<Product> p = addrepo.findAll();
		return p;
	}

	@Override
	public void updateUser() {

	}

	@Override

	public void removeProduct(int id) {
		System.out.println("HELLO");
		try {
			addrepo.deleteById(id);

		} catch (Exception E) {
			E.printStackTrace();
		}
	}

}
