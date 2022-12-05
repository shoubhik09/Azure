package com.azure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.model.Message;

@RestController
public class AzureServiceController {

	@GetMapping("/message")
	public Message getMessage() {
		Message message = new Message();
		message.setCode("1");
		message.setMessage("Azure App Service");
		return message;
	}

}
