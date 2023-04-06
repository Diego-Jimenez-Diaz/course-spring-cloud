package com.djimenez.products.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;


    @Column(name = "create_at")
    private LocalDate createAt;
    
    //Esta anotación se usa para que este atributo no se tome como un campo para la BD
    @Transient
    private Integer port;

    private static final long serialVersionUID = -1714065901434624484L;

}