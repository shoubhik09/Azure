package com.azure.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.azure.model.AddUserRequest;
import com.azure.model.Message;
import com.azure.model.Status;
import com.azure.model.User;
import com.azure.model.UserRequest;
import com.azure.service.AzureService;
//import com.google.api.client.util.IOUtils;
//import com.google.cloud.ReadChannel;

@RestController
public class AzureServiceController {
	
	@Autowired
	private AzureService azureService;
	
	@GetMapping("/message")
	public Message getMessage() {
		Message message = new Message();
		message.setCode("1");
		message.setMessage("Azure App Service");
		return message;
	}
	
	/*@GetMapping("/download-file")
	public String downloadFile(HttpServletResponse response) {
		ReadChannel readChannel = azureService.downloadFile();
        InputStream inputStream = Channels.newInputStream(readChannel);
        try {
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=Shoubhik-Resume.pdf");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
		return "Success";
	}*/
	
	@GetMapping("fetch-all-users")
	public List<User> fetchAllUsers( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		System.out.println("fetchAllUsers");
		List<User> userList = null;
		try {
			userList = azureService.showUsers();
		} catch (Exception e) {
			System.out.println("Error occured while fetchProductById : " + e.getMessage());
		}
		return userList;
	}
	
	@PostMapping("addUser")
	public ResponseEntity<Status> addUser( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody AddUserRequest addUserRequest) {
		System.out.println("addUser");
		Status message = new Status();
		message.setStatusCode(HttpStatus.BAD_REQUEST.value());
		message.setStatusMessage("Error occured while adding the product.");
		try {
			User registeredUser = azureService.findByUserNameOrEmailAddressOrPhoneNumber(addUserRequest.getUserName(), addUserRequest.getEmailAddress(),
					addUserRequest.getPhoneNumber());
			if(null == registeredUser) {
				User user = new User();
				if(addUserRequest.getUserId() != 0) {
					user.setUserId(addUserRequest.getUserId());
				}
				user.setDob(addUserRequest.getDob());
				user.setEmailAddress(addUserRequest.getEmailAddress());
				user.setFirstName(addUserRequest.getFirstName());
				user.setLastName(addUserRequest.getLastName());
				user.setLocation(addUserRequest.getLocation());
				user.setPassword(addUserRequest.getPassword());
				user.setPhoneNumber(addUserRequest.getPhoneNumber());
				user.setStatus("ACTIVE");
				user.setUserName(addUserRequest.getUserName());
				user.setUserType(addUserRequest.getUserType());
				User savedUser = azureService.addUser(user);
				if(savedUser != null) {
					message.setStatusCode(HttpStatus.OK.value());
					message.setStatusMessage("User added successfully.");
				}
			}
		} catch (Exception e) {
			System.out.println("Error occured while addProduct : " + e.getMessage());
		}
		return new ResponseEntity<Status>(message, HttpStatus.OK);
	}
	
	@PostMapping("user-search")
	public ResponseEntity<User> userSearch(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, 
			@RequestBody UserRequest userRequest) {
		System.out.println("userSearch");
		try {
			
			List<User> users = azureService.userSearch(userRequest);
			if(null != users && users.size() > 0) {
				return new ResponseEntity<User>(users.get(0), HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println("Error occured while fetchProductByAdvancedSearch : " + e.getMessage());
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}

}
