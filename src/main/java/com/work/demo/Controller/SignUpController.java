package com.work.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.work.demo.Service.UserService;
import com.work.demo.model.UserInfo;

@Controller
public class SignUpController {
	
	
    @Autowired
	private UserService US;
 
	@PostMapping("/signup")
	public String userSignup(@ModelAttribute UserInfo userinfo, Model model) {
		
		US.addUser(userinfo);
		
		model.addAttribute("message","User added sucessfully");
		
		return "SignUp";
	}
	
	
}
