package com.example.homekeydoor.security;

import com.puzl.user.rest.security.*;
import com.puzl.user.rest.security.service.AIUserDetailsService;
import com.puzl.user.rest.security.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by garik on 5/26/17
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private EntryPointUnauthorizedHandler unauthorizedHandler;

//	@Autowired
//	private CustomerDetailsService customerDetailsService;
//
//	@Autowired
//	private CustomerSecurityService customerSecurityService;

	@Autowired
	private AIUserDetailsService userDetailsService;

	@Autowired
	private UserSecurityService userSecurityService;

	@Autowired
	private AuthenticationTokenService authenticationTokenService;

	@Value("${security.token.header}")
	private String tokenHeader;

	@Value("${security.refreshtoken.header}")
	private String refreshTokenHeader;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AIUserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Bean
	public UserSecurityService userSecurityService() {
		return userSecurityService;
	}


	@Bean
	public UserAuthProvider userAuthProvider() {
		UserAuthProvider agencyAuthProvider = new UserAuthProvider();
		agencyAuthProvider.setUserDetailsService(userDetailsService());
		agencyAuthProvider.setPasswordEncoder(passwordEncoder());
		return agencyAuthProvider;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
//			.antMatchers(HttpMethod.OPTIONS, "/**")
			.antMatchers("/v2/api-docs", "/configuration/ui/**", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		AuthTokenFilter authenticationTokenFilter = new AuthTokenFilter(authenticationTokenService);
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());

		CorsFilter corsFilter = new CorsFilter(tokenHeader, refreshTokenHeader);

		httpSecurity
			.antMatcher("/api/**")
			.csrf()
			.disable()
			.exceptionHandling()
			.authenticationEntryPoint(this.unauthorizedHandler)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.antMatchers("/api/user/authentication/**").permitAll()
			.antMatchers("/api/user/health/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
//				.antMatchers("/api/data/**").permitAll()
//				.antMatchers("/api/users/**").permitAll()
//				.antMatchers("/api/products/**").permitAll()
//				.antMatchers("/api/tags/**").permitAll()
//				.antMatchers("/api/roles/**").permitAll()

			.anyRequest().authenticated()
			.and()
			.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
//			.authenticationProvider(customerAuthProvider())
			.authenticationProvider(userAuthProvider());
	}

}
