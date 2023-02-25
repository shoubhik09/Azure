package com.azure.service;

import java.util.List;

import com.azure.model.User;
//import com.google.cloud.ReadChannel;
import com.azure.model.UserRequest;

public interface AzureService {
	
	//ReadChannel downloadFile();
	
	List<User> showUsers();
	
	User addUser(User user);
	
	List<User> userSearch(UserRequest userRequest);
	
	User findByUserNameOrEmailAddressOrPhoneNumber(String userName, String emailAddress, String phoneNumber);

}
