package com.webapp.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.webapp.service.UserInfoUserDetailsService;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MyConfig {
	
	@Autowired
    private MyJwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
	
	@Bean
	public UserDetailsService userDetailsService() {
//		UserDetails userDetails = User.builder().username("Hari").password(passwordEncoder().encode("1234"))
//				.roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(userDetails);
		return new UserInfoUserDetailsService();
	}
	 
	 @Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	      return  http.csrf().disable()
	    		  .authorizeHttpRequests()
	                .requestMatchers("user_controller/add/user","/auth_controller/login").permitAll()
	                .and()
	                .authorizeHttpRequests().requestMatchers("/")
	                .authenticated().and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
	                and().
	                authenticationProvider(authenticationProvider()).
			addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
			
		}

	    private AuthenticationProvider authenticationProvider() {
		// TODO Auto-generated method stub
	    	DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
	    	daoAuthenticationProvider.setUserDetailsService(userDetailsService());
	    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	    	
		return daoAuthenticationProvider;
	}

		@Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
	        return builder.getAuthenticationManager();
	    }

}
