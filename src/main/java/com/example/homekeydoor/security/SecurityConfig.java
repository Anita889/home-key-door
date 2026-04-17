package com.example.homekeydoor.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final EntryPointUnauthorizedHandler unauthorizedHandler;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationTokenService authenticationTokenService;

	@Value("${security.token.header}")
	private String tokenHeader;

	@Value("${security.refreshtoken.header}")
	private String refreshTokenHeader;

	// ✅ Password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider userAuthProvider() {
		UserAuthProvider provider = new UserAuthProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// ✅ Security filter chain
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		AuthTokenFilter authenticationTokenFilter =
				new AuthTokenFilter(authenticationTokenService);

		CorsFilter corsFilter = new CorsFilter(tokenHeader, refreshTokenHeader);

		http
				.securityMatcher("/api/**")

				.csrf(csrf -> csrf.disable())

				.exceptionHandling(ex -> ex
						.authenticationEntryPoint(unauthorizedHandler)
				)

				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)

				.authorizeHttpRequests(auth -> auth
						// public endpoints
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/api/user/authentication/**").permitAll()
						.requestMatchers("/api/user/health/**").permitAll()
						.requestMatchers("/api/test/**").permitAll()

						// swagger (replacement for web.ignoring)
						.requestMatchers(
								"/v2/api-docs",
								"/swagger-ui/**",
								"/swagger-resources/**",
								"/configuration/**",
								"/webjars/**",
								"/v3/api-docs/**"
						).permitAll()

						.anyRequest().authenticated()
				)

				.authenticationProvider(userAuthProvider())

				.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}