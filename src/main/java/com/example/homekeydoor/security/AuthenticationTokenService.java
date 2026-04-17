package com.example.homekeydoor.security;

import com.example.homekeydoor.consts.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {


	@Value("${security.token.header}")
	private String tokenHeader;

	@Value("${security.token.secret}")
	private String secret;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	public void authenticate(HttpServletRequest request, HttpServletResponse response) {
		String authToken = request.getHeader(tokenHeader);
		if (authToken != null) {
			try {
				TokenUser tokenUser = tokenUtils.getTokenUser(authToken, secret);
				if(tokenUser != null){
					String username = tokenUser.getUsername();
					UserType userType = tokenUser.getUserType();
					if( (userType == UserType.HOME_USER && tokenUser.validToken(UserType.HOME_USER)) ||
						(userType == UserType.HOME_OWNER && tokenUser.validToken(UserType.HOME_OWNER)) ||
						(userType == UserType.ADMIN && tokenUser.validToken(UserType.ADMIN))) {
						UserDetails userDetails = userDetailsService.loadUserByUsername(username);
						UserAuthToken userAuthToken = new UserAuthToken(userDetails, null, userDetails.getAuthorities());
						userAuthToken.setDetails(userDetails);
						SecurityContextHolder.getContext().setAuthentication(userAuthToken);
					}
//					else if(userType == UserType.CUSTOMER && tokenUser.validToken(UserType.CUSTOMER)){
//						UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
//						CustomerAuthToken customerAuthToken = new CustomerAuthToken(userDetails, null, userDetails.getAuthorities());
//						customerAuthToken.setDetails(userDetails);
//						SecurityContextHolder.getContext().setAuthentication(customerAuthToken);
//					}
				}
			} catch (Exception exp) {

			}
		}
	}

}
