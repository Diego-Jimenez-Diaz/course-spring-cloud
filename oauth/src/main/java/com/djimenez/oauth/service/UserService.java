package com.djimenez.oauth.service;

import com.djimenez.commons.users.models.entity.User;

public interface UserService {
	User findByUsername(String username);
}
