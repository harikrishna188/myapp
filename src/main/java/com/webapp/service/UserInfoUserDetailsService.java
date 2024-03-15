package com.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.webapp.model.User;
import com.webapp.repo.UserRepository;
import com.webapp.security.UserInfoForUserDetails;

@Component
public class UserInfoUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		Optional<User> user=userRepository.findByUserName(username);
		
		return user.map(UserInfoForUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User Not Found " +username));
		
	}

}
