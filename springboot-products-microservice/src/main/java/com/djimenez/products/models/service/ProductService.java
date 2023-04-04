package com.djimenez.products.models.service;

import com.djimenez.products.models.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product findById(Long id);
}
