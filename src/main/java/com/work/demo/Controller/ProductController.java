package com.work.demo.Controller;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.work.demo.Repository.AddRepository;
import com.work.demo.Repository.CountRepository;
import com.work.demo.Repository.MyShopRepository;
import com.work.demo.Repository.ProductRepository;
import com.work.demo.Repository.UserInteractionRepository;
import com.work.demo.Repository.UserRepository;
import com.work.demo.Service.ProductService;
import com.work.demo.Service.UserService;
import com.work.demo.model.MyShop;
import com.work.demo.model.Product;
import com.work.demo.model.ProductCatagory;
import com.work.demo.model.ProductReview;
import com.work.demo.model.UserInfo;
import com.work.demo.model.UserInteraction;
import com.work.demo.model.Usercount;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	final int buypoint = 10;
	final int reviewedpoint = 5;
	final String buydetail = "User bought the product";
	final String revieweddetail = "User reviewed the given product";

	@Autowired
	private ProductService ps;

	@Autowired
	private AddRepository ar;

	@Autowired
	private UserRepository ur;

	@Autowired
	private UserService us;

	@Autowired
	private MyShopRepository msrr;

	@Autowired
	private CountRepository cr;

	@Autowired
	private UserInteractionRepository uir;

	@GetMapping("/aDD1")
	public String loadpage(Model model) {
		List<ProductCatagory> lpc = ps.loadAllProducts();
		model.addAttribute("catagory", lpc);
		return "AddProduct";
	}

	@PostMapping("/addproducts")
	public String addproducts(@ModelAttribute Product product, @RequestParam String str, MultipartFile profile,
			Model model, HttpSession session) {

		if (profile != null) {
			try {
				Files.copy(profile.getInputStream(),
						Path.of("src/main/resources/static/ProductImages/" + profile.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				String name = profile.getOriginalFilename();
				product.setImage(name);
				MyShop ms = (MyShop) session.getAttribute("shopuser");
				System.out.println(ms.getShopname());
				if (ms != null) {
					product.setShop(ms);

				}
				ps.addProducts(product);

				List<ProductCatagory> lpc = ps.loadAllProducts();
				model.addAttribute("catagory", lpc);

				model.addAttribute("name", lpc);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
		if (str.equalsIgnoreCase("shop")) {
			return "ShopProductAdd";
		} else {
			return "AddProduct";
		}
	}

	@GetMapping("/addtocart")

	public String addToCart(@RequestParam int id, @RequestParam int shopid, HttpSession session, Model model) {
		UserInfo ui = (UserInfo) session.getAttribute("user");

		if (ui != null) {
			List<Product> npl = ui.getCartitem();

			Product p = ar.getById(id);
			List<UserInfo> nul = p.getLinkeduser();
			npl.add(p);

			ui.setCartitem(npl);

			ur.save(ui);
			List<Product> product = ps.showAllProduct();
			System.out.println(product.size());

			List<Usercount> uci = ui.getUserCounts();
			Usercount fu = uci.stream().filter(fetchuser -> fetchuser.getMyShop().getId() == shopid
					&& fetchuser.getUserInfo().getId() == ui.getId()).findFirst().orElse(null);

			if (fu == null) {
				Usercount ucc = new Usercount();
				ucc.setMyShop(msrr.getById(shopid));
				ucc.setUserInfo(ui);
				ucc.setCount(ucc.getCount() + 1);
				cr.save(ucc);
			} else {
				fu.setCount(fu.getCount() + 10);
				cr.save(fu);
			}
			List<Product> products = product.subList(1, 5);
			List<Product> productss = product.subList(1, 5);
			List<Product> productsss = product.subList(1, 2);

			model.addAttribute("data", products);
			model.addAttribute("datas", productss);
			model.addAttribute("singledata", productsss);

			return "LoggedDashBoard";
		}
		return "LoginForm";
	}

	@GetMapping("/cart")

	public String loadCart(HttpSession session, Model model) {
		UserInfo ui = (UserInfo) session.getAttribute("user");
		List<Product> lop = ui.getCartitem();
		model.addAttribute("list", lop);
		return "Cart";
	}

	@GetMapping("/remove")
	public String removeFromCart(@RequestParam int id, HttpSession session, Model model) {
		UserInfo ui = (UserInfo) session.getAttribute("user");
		List<Product> lop = ui.getCartitem();

		// Remove the product from the list
		lop.removeIf(product -> product.getId() == id);

		// Save the updated list to the UserInfo object
		ui.setCartitem(lop);

		// Save the updated UserInfo object to the database
		ur.save(ui);

		List<Product> los = ui.getCartitem();
		model.addAttribute("list", los);

		return "Cart";
	}

	@GetMapping("/seemore")
	public String loadDetailPage(HttpSession session, Model model) {
		UserInfo ui = (UserInfo) session.getAttribute("user");

		List<Product> p = ps.showAllProduct();
		model.addAttribute("products", p);

		return "DetailPage";
	}

	@GetMapping("/removeproduct")
	public String removeProduct(@RequestParam int id, HttpSession session) {
		UserInfo ui = (UserInfo) session.getAttribute("user");
		Product pp = ar.findById(id).orElse(null);
		List<UserInfo> uil = ur.findByCartitemContaining(pp);
		List<Product> pro = new ArrayList<>();
		for (UserInfo userinfo : uil) {
			userinfo.getCartitem().removeIf(Product -> Product.getId() == id);
			ur.save(userinfo);
		}

		pro = ui.getCartitem();
		pro.removeIf(Product -> Product.getId() == id);
		ui.setCartitem(pro);
		ur.save(ui);
		ps.removeProduct(id);

		return "Product";
	}

	@GetMapping("/checkout")
	public String performCheckout(HttpSession session, Model model) {
		UserInfo ui = (UserInfo) session.getAttribute("user");
		Hibernate.initialize(ui.getOrderplaced());
		List<Product> pp = ui.getOrderplaced();
		List<Product> cartItems = ui.getCartitem();
		List<Product> orderPlacedItems = new ArrayList<>(cartItems);
		orderPlacedItems.addAll(pp);

		ui.setOrderplaced(orderPlacedItems);

		ui.setCartitem(cartItems);

		System.out.println("HELLO");
		us.addUser(ui);

		for (Product p : cartItems) {
			UserInteraction uin = new UserInteraction();

			uin.setProduct(p);
			int shopid = p.getShop().getId();

			MyShop myshop = msrr.getById(shopid);
			uin.setUserinfos(ui);
			uin.setGainedpoints(buypoint);
			uin.setDescription(buydetail);

			uir.save(uin);
			
			p.setPopularitycount(p.getPopularitycount()+1);
			ar.save(p);

		}

		model.addAttribute("list", orderPlacedItems);

		return "Cart";
	}

	@GetMapping("/proceedtocheckout")
	public String proceedToCheckout(HttpSession session, Model model) {

		UserInfo ui = (UserInfo) session.getAttribute("user");
		List<Product> lop = ui.getCartitem();

		model.addAttribute("orderproducts", lop);

		model.addAttribute("userinfo", ui);

		return "Address";
	}

	@GetMapping("/editdetails")
	public String editUserDetails(@ModelAttribute UserInfo userinfo, Model model, HttpSession session) {
		UserInfo ui = (UserInfo) session.getAttribute("user");
		List<Product> gci = ui.getCartitem();
		userinfo.setCartitem(gci);
		ur.save(userinfo);
		UserInfo newuser = ur.getById(userinfo.getId());
		session.setAttribute("user", newuser);
		List<Product> lop = newuser.getCartitem();

		model.addAttribute("orderproducts", lop);

		model.addAttribute("userinfo", newuser);
		return "Address";
	}

	@GetMapping("/detailedorderinfo")
	public String fetchProductInfo() {

		return "";
	}
	
	
	@GetMapping("/productdetail")
	public String loadProductDetail(@RequestParam int id, Model model) {
		
		Product p= ar.getById(id);
		model.addAttribute("product", p);
		return "ProductDetailPage";
	}
	
	
	
	@GetMapping("/getproductreview")
	public String saveProductReview(@RequestParam int pid,@ModelAttribute ProductReview review, Model model,HttpSession session) {
	UserInfo ui  =	(UserInfo) session.getAttribute("user");
	review.setUser(ui);
	review.setProduct(ar.getById(pid));
		return "redirect:/productdetail";
	}
}
