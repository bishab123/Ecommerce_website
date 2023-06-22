package com.work.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.work.demo.Repository.UserRepository;
import com.work.demo.Service.ProductService;
import com.work.demo.model.Product;
import com.work.demo.model.UserInfo;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private UserRepository UR;

	@Autowired
	private ProductService pc;

	@GetMapping("/login")
	public String openLogin() {
		return "LoginForm";
	}

	@GetMapping("/signup")
	public String loadSignUp() {
		return "SignUp";
	}

	@PostMapping("/loginuser")
	public String checkLogin(@ModelAttribute UserInfo userinfo, Model model, HttpSession session) {
		UserInfo loggeduser = UR.findByUsernameAndPassword(userinfo.getUsername(), userinfo.getPassword());

		if (loggeduser != null) {
			List<Product> product = pc.showAllProduct();
			List<Product> products = product.subList(0, 5);
			List<Product> productss = product.subList(1, 5);
			List<Product> productsss = product.subList(1, 2);
			session.setAttribute("user", loggeduser);
			session.setMaxInactiveInterval(20000);
			model.addAttribute("data", products);
			model.addAttribute("datas", productss);
			model.addAttribute("singledata", productsss);
			return "LoggedDashBoard";
		} else {
			return "LoginForm";
		}
	}

	@GetMapping("/logout")
	public String loadLandingPage(HttpSession session) {
		session.invalidate();
		return "LandingPage";

	}

	@GetMapping("/loggedhome")
	public String loadHome(Model model) {
		List<Product> product = pc.showAllProduct();
		List<Product> products = product.subList(0, 5);
		List<Product> productss = product.subList(1, 5);
		List<Product> productsss = product.subList(1, 2);
		model.addAttribute("data", products);
		model.addAttribute("datas", productss);
		model.addAttribute("singledata", productsss);
		return "LoggedDashBoard";
	}
}
