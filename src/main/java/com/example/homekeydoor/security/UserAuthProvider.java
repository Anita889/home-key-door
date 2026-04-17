package com.example.homekeydoor.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;


public class UserAuthProvider extends DaoAuthenticationProvider {


	//added
	public UserAuthProvider(UserDetailsService userDetailsService) {
		super(userDetailsService);
	}

	public boolean supports(Class<?> authentication) {
		return (UserAuthToken.class.equals(authentication));
	}
}
