package com.djimenez.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{

	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		log.info("Success login: " + user.getUsername());
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.info("Error login: " + exception.getMessage());
		
	}
	
}
