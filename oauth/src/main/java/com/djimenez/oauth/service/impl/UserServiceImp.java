package com.djimenez.oauth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.djimenez.commons.users.models.entity.User;
import com.djimenez.oauth.clients.UserFeignClient;
import com.djimenez.oauth.service.UserService;

import feign.FeignException;

@Service
public class UserServiceImp implements UserDetailsService, UserService{

	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

	
	@Autowired
	private UserFeignClient client;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			
			User user = client.findByUsername(username);
			
			List<GrantedAuthority> authohrities = user.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.peek(authority -> log.info("Role: " + authority.getAuthority()))
					.collect(Collectors.toList());
			
			log.info("User authenticated: " + username);
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authohrities);
		} catch (FeignException e) {
			
			log.info("Error in the login, user '" + username + "' not found in the system");
			throw new UsernameNotFoundException("Error in the login, user '" + username + "' not found in the system");
		}
	}

	@Override
	public User findByUsername(String username) {
		return client.findByUsername(username);
	}

	@Override
	public User update(User user, Long id) {
		return client.update(user, id);
	}

}
