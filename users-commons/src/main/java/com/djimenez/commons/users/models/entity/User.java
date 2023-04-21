package com.djimenez.commons.users.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 20)
	private String username;
	
	@Column(length = 60)
	private String password;
	
	private Boolean enabled;
	private String first_name;
	private String last_name;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_to_roles", joinColumns =  @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"),
	uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
	private List<Role> roles;
	
	
	@Column(unique = true, length = 20)
	private String email;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4204474403866820596L;

}
