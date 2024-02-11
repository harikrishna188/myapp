package com.webapp.controller;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.request.UserRequest;
import com.webapp.response.SaveUserResponse;
import com.webapp.service.UserService;
@RestController
@RequestMapping("user_controller")
public class UserController {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@PostMapping("/add/user")
	public ResponseEntity<SaveUserResponse> addNewUser(@RequestBody UserRequest userRequest) {
		ResponseEntity<SaveUserResponse> saveUserResponse = null;
		SaveUserResponse user = new SaveUserResponse();
	    try {
	      user = userService.saveUser(userRequest);
			if(user !=null) {
				saveUserResponse=new ResponseEntity<SaveUserResponse>(user, HttpStatus.OK);
			} else {
				saveUserResponse = new ResponseEntity<SaveUserResponse>(HttpStatus.BAD_REQUEST);
				throw new Exception();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			saveUserResponse = new ResponseEntity<SaveUserResponse>(HttpStatus.BAD_REQUEST);
		}
		return saveUserResponse;
	}

	
}
