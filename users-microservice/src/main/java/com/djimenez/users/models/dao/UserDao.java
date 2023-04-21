package com.djimenez.users.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.djimenez.users.models.entity.User;

/*Acortamos el CRUD repository con la dependenciaSpring Boot starter Data Rest*/
@RepositoryRestResource(path = "users")
public interface UserDao extends JpaRepository<User, Long>{
	
	@RestResource(path = "searchByUsername")
	public User findByUsername(@Param("username") String username);
}
