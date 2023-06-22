package com.work.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.work.demo.Service.ProductService;
import com.work.demo.model.Product;

@Controller

public class LandingPageController {
	
	
	
	@Autowired
	private ProductService pc;
	@GetMapping("/")
    public String loadLandingPage(Model model) {
		
	
    	return "LandingPage";
    }
	
	
	@GetMapping("/aboutus")
	public String loadAboutus() {
		return "AboutUs";
	}
	
	
	@GetMapping("/footer")
	public String loadfooter() {
		return "Footer";
	}
}
