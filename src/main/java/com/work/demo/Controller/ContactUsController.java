package com.work.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.work.demo.MyUtil.Util;

@Controller
public class ContactUsController {
	
	
	@Autowired
	private Util util;

	@GetMapping("/contactus")
	public String loadContactPage() {
		return "ContactUs";
	}

	@PostMapping("/contactus")
	public String sendEmail(@RequestParam String name, @RequestParam String email, @RequestParam String subject,
			@RequestParam String body, @RequestParam String To_email) {
		
		util.sendEmailToUs(name, email, subject, body, To_email);
		return "ContactUs";
	}

}
