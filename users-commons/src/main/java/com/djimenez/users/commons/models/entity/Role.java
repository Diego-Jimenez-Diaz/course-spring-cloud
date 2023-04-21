package com.djimenez.users.commons.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name = "roles")
public class Role implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 30)
	private String name;
	
	/*
	 * Para implementar un Many to Many en ambos sentidos debemos declarar otra lista
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private List<User> users;*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2829314934656155282L;

}
