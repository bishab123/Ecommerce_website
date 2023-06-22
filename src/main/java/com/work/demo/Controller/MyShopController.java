package com.work.demo.Controller;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

import org.hibernate.Hibernate;
import org.hibernate.Remove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.work.demo.MyUtil.Util;
import com.work.demo.Repository.AddRepository;
import com.work.demo.Repository.CountRepository;
import com.work.demo.Repository.MyShopRepository;
import com.work.demo.Repository.RemoveRepository;
import com.work.demo.Repository.ReviewRepository;
import com.work.demo.Repository.UserRepository;
import com.work.demo.Service.ProductService;
import com.work.demo.Service.ShopService;
import com.work.demo.Service.UserService;
import com.work.demo.UserServiceImpl.ProductServiceImpl;
import com.work.demo.model.MyShop;
import com.work.demo.model.Product;
import com.work.demo.model.ProductCatagory;
import com.work.demo.model.ShopReview;
import com.work.demo.model.UserInfo;
import com.work.demo.model.Usercount;

import jakarta.servlet.http.HttpSession;

@Controller

public class MyShopController {

	@Autowired
	private UserService US;

	@Autowired
	private ProductService ps;

	@Autowired
	private CountRepository cr;

	@Autowired
	private ProductServiceImpl psi;
	@Autowired
	private Util util;

	@Autowired
	private ShopService ss;

	@Autowired
	private MyShopRepository msr;

	@Autowired
	private AddRepository ar;

	@Autowired
	private RemoveRepository rr;

	@Autowired
	private UserRepository ur;

	@Autowired
	private ReviewRepository rer;

	@GetMapping("/myshop")
	public String loadMyshop() {
		return "CreateYourShop";
	}

	@GetMapping("/logintoshop")
	public String loginToShop() {

		return "ShopLogin";
	}

	@PostMapping("/logintoshop")
	public String loadShopDashboard(@ModelAttribute MyShop shop, Model model, HttpSession session) {
		MyShop my = msr.findByShopusernameAndShopcode(shop.getShopusername(), shop.getShopcode());

		if (my != null) {

			List<Usercount> ucc = my.getUserCounts();
			System.out.println(ucc.size());
			Collections.sort(ucc, Comparator.comparingInt(Usercount::getCount).reversed());
			List<UserInfo> lui = new ArrayList<>();
			for (Usercount uc : ucc) {
				UserInfo uii = uc.getUserInfo();
				lui.add(uii);

			}

			System.out.println(lui.size());

			List<Product> p = my.getProduct();
			session.setAttribute("shopuser", my);
			model.addAttribute("shop", my);
			model.addAttribute("products", p);
			model.addAttribute("users", lui);
			return "ShopDashBoard";
		}
		return "ShopLogin";
	}

	@GetMapping("/createyourshop")
	public String loadSignupShop() {
		return "ShopSignUp";
	}

	@PostMapping("/signupshop")
	public String signUserUp(@ModelAttribute MyShop shop, Model model, HttpSession session, MultipartFile image) {
		String uniquecode = util.generateUniqueCode();

		shop.setShopcode(uniquecode);

		UserInfo ui = (UserInfo) session.getAttribute("user");
		shop.setUserinfo(ui);
		System.out.println(ui.getFname());

		MyShop mys = ui.getShop();

		if (mys != null) {

			model.addAttribute("exist", "Shop for this user already exists");

			return "ShopSignUp";

		} else {
			if (image != null) {
				try {
					Files.copy(image.getInputStream(),
							Path.of("src/main/resources/static/ShopImages/" + image.getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);

					String LOGO = image.getOriginalFilename();
					System.out.println(LOGO);
					shop.setShoplogo(LOGO);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			ss.createShop(shop);

			model.addAttribute("uniquecode", uniquecode);

			model.addAttribute("msg", "Shop created sucessfully");
			return "ShopSignUp";
		}

	}

	@GetMapping("/addproductsfromshop")
	public String addProductsFromShop(Model model) {
		List<ProductCatagory> lpc = ps.loadAllProducts();
		model.addAttribute("catagory", lpc);
		return "ShopProductAdd";
	}

	@PostMapping("/editShopInfo")
	public String editShopInfo(@ModelAttribute MyShop shop, Model model, HttpSession session, MultipartFile image) {
		shop.setShoplogo(image.getOriginalFilename());
		System.out.println("bishab");
		if (image != null) {
			try {
				Files.copy(image.getInputStream(),
						Path.of("src/main/resources/static/ShopImages/" + image.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				System.out.println(image.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		UserInfo uis = (UserInfo) session.getAttribute("user");
		System.out.println(uis.getFname());
		shop.setUserinfo(uis);
		ss.createShop(shop);
		MyShop mys = (MyShop) session.getAttribute("shopuser");

		int id = mys.getId();
		MyShop my = ss.getShop(id);

		if (my != null) {
			System.out.println(my.getShopname());
			List<UserInfo> ui = US.getAllUser();
			List<Product> p = mys.getProduct();

			System.out.println(p.size());

			model.addAttribute("shop", my);
			model.addAttribute("products", p);
			model.addAttribute("users", ui);
			return "ShopDashBoard";
		}
		return "ShopLogin";

	}

	@GetMapping("/loggedshop")
	public String loadLoggedShop(HttpSession session, Model model) {
		MyShop my = (MyShop) session.getAttribute("shopuser");
		List<UserInfo> ui = US.getAllUser();
		List<Product> p = my.getProduct();
		model.addAttribute("shop", my);
		model.addAttribute("products", p);
		model.addAttribute("users", ui);
		return "ShopDashBoard";

	}

	@GetMapping("/manageorder")
	public String manageYourOrder(HttpSession session, Model model) {
		MyShop mys = (MyShop) session.getAttribute("shopuser");
		List<Product> p = mys.getProduct();
		model.addAttribute("products", p);

		return "productManagement";
	}

	@GetMapping("/removed")
	public String productRemove(@RequestParam int id, HttpSession session, Model model) {
		MyShop mys = (MyShop) session.getAttribute("shopuser");
		List<Product> p = mys.getProduct();

		// Remove the product from the database
		rr.deleteById(id);

		// Remove the product from the list
		p.removeIf(product -> product.getId() == id);

		model.addAttribute("products", p);
		return "ProductManagement";
	}

	@GetMapping("/orderreceived")
	public String loadOrderReceived(HttpSession session, Model model) {
		Multimap<Product, UserInfo> bigmap = ArrayListMultimap.create();
		MyShop mys = (MyShop) session.getAttribute("shopuser");
		List<Product> p = mys.getProduct();

		if (p != null) {
			for (Product singleproduct : p) {
				Hibernate.initialize(singleproduct.getAddeduser());

				List<UserInfo> userlist = singleproduct.getAddeduser();
				if (userlist != null) {
					for (UserInfo pulleduser : userlist) {
						bigmap.put(singleproduct, pulleduser);
					}
				}
			}
		}

		System.out.println(bigmap.size());
		model.addAttribute("orderreceived", bigmap);
		return "ShopOrderReceived";
	}

	@GetMapping("/deleteshop")
	public String deleteShop(HttpSession session) {
		MyShop ms = (MyShop) session.getAttribute("shopuser");
		msr.delete(ms);
		return "LoginForm";

	}

	@GetMapping("/confirmedorder")
	public String confirmOrder(@RequestParam int id, @RequestParam int userid, Model model, HttpSession session) {
		List<UserInfo> uil = new ArrayList<>();
		List<Product> pl = new ArrayList<>();
		MyShop mys = (MyShop) session.getAttribute("shopuser");
		int ids = mys.getId();
		MyShop myshop = msr.getById(ids);

		UserInfo ui = ur.getById(userid);
		List<Product> gop = ui.getOrderplaced();
		pl.addAll(gop);
		List<Product> p = ui.getOrderplaced();
		p.removeIf(products -> products.getId() == id);
		Product pp = ar.getById(id);
		uil.add(ui);
		pl.add(pp);
		ui.setOrderplaced(p);
		ui.setConfirmedorder(pl);
		pp.setAddedusers(null);

		ar.save(pp);

		ur.save(ui);
		session.setAttribute("shopuser", myshop);
		model.addAttribute("message", "order-Confirmed sucessfully");

		return "redirect:/orderreceived";
	}

	@GetMapping("/ShopPublicDashBoard")

	public String loadPublicShopDashBoard(@RequestParam int shopid, HttpSession session, Model model) {
		MyShop ms = msr.getById(shopid);
		List<Product> p = ms.getProduct();

		if (p.size() >= 5) {
			List<Product> sublist = p.subList(1, 5);
			model.addAttribute("List", sublist);
		} else {
			model.addAttribute("List", p);
		}

		UserInfo ui = (UserInfo) session.getAttribute("user");

		List<Usercount> ucs = ui.getUserCounts();
		Usercount guc = ucs.stream()
				.filter(uc -> uc.getMyShop().getId() == shopid && uc.getUserInfo().getId() == ui.getId()).findFirst()
				.orElse(null);

		if (guc == null) {

			Usercount cu = new Usercount();
			cu.setUserInfo(ui);
			cu.setMyShop(ms);
			cu.setCount(cu.getCount() + 1);
			cr.save(cu);

		} else {
			guc.setCount(guc.getCount() + 1);
			cr.save(guc);
		}
		List<ShopReview> sr = rer.findAll();

		List<ShopReview> fsr = sr.stream().filter(Shop -> Shop.getUser().getId() == ui.getId())
				.collect(Collectors.toList());

		System.out.println(fsr.size());

		model.addAttribute("shop", ms);
		model.addAttribute("review", fsr);
		model.addAttribute("message", "hello");
		return "ShopPublicDashBoard";
	}
}
