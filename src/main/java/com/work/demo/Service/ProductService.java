package com.work.demo.Service;

import java.util.List;

import com.work.demo.model.Product;
import com.work.demo.model.ProductCatagory;

public interface ProductService {
   public void addProducts(Product ps);
	
	 public List<ProductCatagory> loadAllProducts(); 
	 public List<Product> showAllProduct();
	 
	 public void updateUser();

	void removeProduct(int id);
	 
}
