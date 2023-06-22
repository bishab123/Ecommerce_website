package com.work.demo.Controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.work.demo.Repository.AddRepository;
import com.work.demo.Repository.CountRepository;
import com.work.demo.Repository.MyShopRepository;
import com.work.demo.Repository.ProductRepository;
import com.work.demo.model.MyShop;
import com.work.demo.model.Product;
import com.work.demo.model.ProductCatagory;
import com.work.demo.model.UserInfo;
import com.work.demo.model.Usercount;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductByCatagoryController {

	@Autowired
	private ProductRepository ar;

	@Autowired
	private AddRepository pr;

	@Autowired
	private CountRepository cr;

	@Autowired
	private MyShopRepository msr;

	@GetMapping("/products")
	public String loadAllProducts() {
		return "Product";
	}

	@GetMapping("/men")
	public String loadMensProduct(@RequestParam String catagory, Model model) {
		ProductCatagory p = ar.findByCatagoryname(catagory);
		List<Product> gp = p.getProduct();
		model.addAttribute("list", gp);
		return "Product";
	}

	@GetMapping("/electronics")
	public String loadElectronicsProduct(@RequestParam String catagory, Model model) {
		ProductCatagory p = ar.findByCatagoryname(catagory);
		List<Product> gp = p.getProduct();
		model.addAttribute("list", gp);
		return "Product";
	}

	@GetMapping("/summer")
	public String loadSummerProduct(@RequestParam String catagory, Model model) {
		ProductCatagory p = ar.findByCatagoryname(catagory);
		List<Product> gp = p.getProduct();
		model.addAttribute("list", gp);
		return "Product";
	}

	@GetMapping("/new-arrivals")
	public String loadNewArrivalsProduct(@RequestParam String catagory, Model model) {
		ProductCatagory p = ar.findByCatagoryname(catagory);
		List<Product> gp = p.getProduct();
		model.addAttribute("list", gp);
		return "Product";
	}

	@GetMapping("/winter")
	public String loadWinterProduct(@RequestParam String catagory, Model model) {
		ProductCatagory p = ar.findByCatagoryname(catagory);
		List<Product> gp = p.getProduct();
		model.addAttribute("list", gp);
		return "Product";
	}

	@GetMapping("/sales")
	public String loadSalesProduct(@RequestParam String catagory, Model model) {
		ProductCatagory p = ar.findByCatagoryname(catagory);
		List<Product> gp = p.getProduct();
		model.addAttribute("list", gp);
		return "Product";
	}

	@GetMapping("/search")
	public String loadDataOfSearch(@RequestParam("query") String query, Model model) {
		List<Product> sp = pr.findByNameContaining(query);
		model.addAttribute("list", sp);
		return "Product";

	}

	@GetMapping("/shopdetail")
	public String loadShopDetail(@RequestParam int id, HttpSession session) {

		UserInfo ui = (UserInfo) session.getAttribute("user");
		MyShop my = msr.getById(id);
		List<Usercount> ucs = ui.getUserCounts();
		Usercount guc = ucs.stream()
				.filter(uc -> uc.getMyShop().getId() == id && uc.getUserInfo().getId() == ui.getId()).findFirst()
				.orElse(null);

		if (guc == null) {

			Usercount cu = new Usercount();
			cu.setUserInfo(ui);
			cu.setMyShop(my);
			cu.setCount(cu.getCount() + 1);
			cr.save(cu);

		} else {
			guc.setCount(guc.getCount() + 1);
			cr.save(guc);
		}
		return "Product";
	}
}
