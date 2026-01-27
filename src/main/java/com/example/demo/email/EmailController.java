package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private EmailPort emailPort;
	
	@PostMapping("/send")
	public boolean sendEmail(@RequestBody EmailBody emailBody) {
		return emailPort.sendEmail(emailBody);
	}

}
