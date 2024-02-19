package com.webapp.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.security.JwtHelper;
import com.webapp.security.JwtRequest;
import com.webapp.security.JwtResponse;


@RestController
@RequestMapping("auth_controller/")
public class AuthenticationController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private AuthenticationManager AuthenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
		String token = this.jwtHelper.generateToken(userDetails);
		JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
		return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
	}

	private void doAuthenticate(String email, String password) {
		// TODO Auto-generated method stub

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,password);
		try {
			AuthenticationManager.authenticate(authentication);
			
		}catch(BadCredentialsException e) {
			throw new RuntimeException("Invalid user or password...");
		}
		
	}
	
}
