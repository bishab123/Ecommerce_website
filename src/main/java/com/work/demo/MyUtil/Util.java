package com.work.demo.MyUtil;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Util {

	@Autowired
	private JavaMailSender javamailsender;

	public void sendEmailToUs(String name, String email, String subject, String body, String To_email) {

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setTo(To_email);
		msg.setSubject("(" + name + ")" + subject + "USEREMAIL IS=" + "(" + email + ")");
		msg.setText(body);
		javamailsender.send(msg);
	}

	public String generateUniqueCode() {
		// Generate a random UUID
		UUID uuid = UUID.randomUUID();
		// Get the string representation of the UUID
		String uniqueCode = uuid.toString();
		// Remove any hyphens or dashes from the UUID string
		uniqueCode = uniqueCode.replace("-", "");
		// Return the unique code
		return uniqueCode;
	}
}
