package com.djimenez.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.djimenez.commons.users.models.entity.User;
import com.djimenez.oauth.service.UserService;

import feign.FeignException;


@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{

	private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		log.info("Success login: " + user.getUsername());
		
		User userFind = userService.findByUsername(authentication.getName());
		if (userFind.getAttemps() != null && userFind.getAttemps() > 0) {
			userFind.setAttemps(0);
			userService.update(userFind, userFind.getId());
			log.info("Attemps restarted");
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.info("Error login: " + exception.getMessage());
		
		try {
			User user = userService.findByUsername(authentication.getName());
			if (user.getAttemps() == null) {
				user.setAttemps(0);
			}
			
			user.setAttemps(user.getAttemps() + 1);
			log.info(String.format("User attem: %s", user.getAttemps()));
			
			if(user.getAttemps()>=3) {
				log.info(String.format("User %s disabled due to max attemps", authentication.getName()));
				user.setEnabled(false);
			}
			
			userService.update(user, user.getId());
			
		} catch(FeignException e) {
			log.info(String.format("El usuario %s no existe en sistema", authentication.getName()));
		}
		
	}
	
}
