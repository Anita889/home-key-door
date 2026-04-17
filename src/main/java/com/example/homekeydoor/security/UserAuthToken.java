package com.example.homekeydoor.security;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by garik on 5/26/17
 */
public class UserAuthToken extends UsernamePasswordAuthenticationToken {

	public UserAuthToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public UserAuthToken(Object principal, Object credentials,
						 Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

}
