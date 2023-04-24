package com.djimenez.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.djimenez.commons.users.models.entity.User;

@FeignClient("users-microservice")
public interface UserFeignClient {

	@GetMapping("/users/search/findByUsername")
	public User findByUsername(@RequestParam("username") String username);
	
	@PutMapping("/users/{id}")
	public User update(@RequestBody User user, @PathVariable("id") Long id);
}
