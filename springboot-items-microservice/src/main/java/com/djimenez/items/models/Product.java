package com.djimenez.items.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Product {

    private Long id;
    private String name;
    private Double price;
    private LocalDate createAt;
    private Integer port;

}
