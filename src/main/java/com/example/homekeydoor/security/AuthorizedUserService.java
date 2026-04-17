package com.example.homekeydoor.security;

import com.example.homekeydoor.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizedUserService {

	public User getAuthorizedUser() {
		SecureUser secureUser = (SecureUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();
		return secureUser.getUserEntity();
	}

}



