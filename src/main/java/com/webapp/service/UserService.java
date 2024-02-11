package com.webapp.service;

import com.webapp.request.UserRequest;
import com.webapp.response.SaveUserResponse;

public interface UserService {
	
	SaveUserResponse saveUser(UserRequest userRequest);

}
