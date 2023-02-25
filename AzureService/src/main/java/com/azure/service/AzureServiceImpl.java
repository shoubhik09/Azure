package com.azure.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.model.User;
import com.azure.model.UserRequest;
import com.azure.repo.UserRepository;
/*import com.google.api.gax.paging.Page;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;*/

@Service
public class AzureServiceImpl implements AzureService {
	
	/*@Autowired
	private Storage storage;*/
	
	@Autowired
	private UserRepository userRepository;

	/*@Override
	public ReadChannel downloadFile() {
		ReadChannel readChannel = null;
		Bucket bucket = storage.get("shoubhik-resume");
		bucket.list();
		Page<Blob> blobs = bucket.list();
		for (Blob blob: blobs.getValues()) {
			readChannel = blob.reader();
			break;
		}   
		return readChannel;
	}*/

	@Override
	public List<User> showUsers() {
		return (List<User>) userRepository.findAll(); 
	}
	
	@Override
	public User addUser(User user) { 
		 return userRepository.save(user);
	}
	
	@Override
	public List<User> userSearch(UserRequest userRequest) { 
		return userRepository.userSearch(userRequest.getUserName(), userRequest.getPassword());
	}
	
	@Override
	public User findByUserNameOrEmailAddressOrPhoneNumber(String userName, String emailAddress, String phoneNumber) {
		return userRepository.findByUserNameOrEmailAddressOrPhoneNumber(userName, emailAddress, phoneNumber);
	}
	
}
