package com.webapp.serviceImpl;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.model.User;
import com.webapp.repo.UserRepository;
import com.webapp.request.UserRequest;
import com.webapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public String saveUser(UserRequest userRequest) {
		LOGGER.info("Entry :: Inside UserServiceImpl :: saveUser():");

		String message = null;
		User user = new User();
		if (userRequest != null) {
			BeanUtils.copyProperties(userRequest, user);
		}
		if (userRequest.getEmailId() != null && userRequest.getUserPassword() != null
				&& !userRequest.getEmailId().isEmpty() && !userRequest.getUserPassword().isEmpty()) {
			message = addUserValidate(userRequest);

		}

		LOGGER.info("Exit :: Inside UserServiceImpl :: saveUser():");
		return message;
	}

	private String addUserValidate(UserRequest userRequest) {
		LOGGER.info("Entry :: Inside UserServiceImpl :: addUserValidate():");

		String message = null;
		User user = new User();
		{
			String email = userRequest.getEmailId();

			Optional<User> userReport = userRepository.findByEmail(email);
			try {
				if (userRequest.getUserId() == 0 && userReport.isPresent()) {
					message = "user already existed";
					throw new Exception("user already existed");
				} else if (userRequest.getUserId() != 0 && userReport.isPresent()) {
					message = "user updated successfully";
					Optional<User> userEmail = userRepository.findByEmail(email);
					if (!userEmail.isPresent()&&userEmail.get().getUserId()!=userRequest.getUserId()) {
						BeanUtils.copyProperties(userRequest, user);
						user.setCreatedDate(userReport.get().getCreatedDate());
					} else {
						message = "email already existed";
						throw new Exception("email already existed");
					}
				} else {
					message = "user saved successfully";
					BeanUtils.copyProperties(userRequest, user);

				}
				user = userRepository.save(user);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

		}
		LOGGER.info("Exit :: Inside UserServiceImpl :: addUserValidate():");

		return message;
	}

}
