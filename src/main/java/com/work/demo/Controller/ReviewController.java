package com.work.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.work.demo.Repository.MyShopRepository;
import com.work.demo.Repository.ReviewRepository;
import com.work.demo.model.MyShop;
import com.work.demo.model.ShopReview;
import com.work.demo.model.UserInfo;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReviewController {

	@Autowired
	private ReviewRepository reviewrepo;
	
	
	@Autowired
	private MyShopRepository msr;

	@PostMapping("/getreview")

	public String getReview(@ModelAttribute ShopReview review, @RequestParam int shopid, HttpSession session) {

		UserInfo ui = (UserInfo) session.getAttribute("user");
		review.setUser(ui);
		MyShop shop=msr.getById(shopid);
		review.setShop(shop);
		reviewrepo.save(review);

		return "redirect:/ShopPublicDashBoard?shopid=" + shopid;

	}

}
