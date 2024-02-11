package com.webapp.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.model.User;
import com.webapp.repo.UserRepository;
import com.webapp.request.UserRequest;
import com.webapp.response.SaveUserResponse;
import com.webapp.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public SaveUserResponse saveUser(UserRequest userRequest) {

		SaveUserResponse saveUserResponse = new SaveUserResponse();
		User user = new User();
		BeanUtils.copyProperties(userRequest, user);
		user = userRepository.save(user);
		BeanUtils.copyProperties(user, saveUserResponse);
		return saveUserResponse;
	}

}
