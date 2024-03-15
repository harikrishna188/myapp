package com.webapp.service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
//	@Autowired
//	private UserDetails userDetail;
	
	String secrect = "771d38bedb9b320f7951d356437d7c58db3762955c7a2088acfb49f9e5d8289c";
	public String generateToken(String userName) {
		Map<String,Object> claims = new HashMap<>();
		return createToken(claims,userName);
		
	}

	private String createToken(Map<String, Object> claims, String userName) {
	
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
		
	}

	private Key getSignKey() {
		byte[] keyBytes= Decoders.BASE64.decode(secrect);
		return   Keys.hmacShaKeyFor(keyBytes);
	}
	

	public Boolean validateToken(String token, UserDetails userDetails) {
	
		final String userName=extractUsername(token);
		
		return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		
		return false;
	}

	public String extractUsername(String token) {
		
		return extractClaim(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}

	public <T> T extractClaim(String token,Function<Claims,T> claimsResoler) {
		final Claims claims =extractAllClaims(token);
	
		return claimsResoler.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
