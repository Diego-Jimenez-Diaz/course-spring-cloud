package com.djimenez.products.models.service;

import com.djimenez.products.models.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product findById(Long id);
    Product save(Product product);
    Product update(Long id, Product product);
    void deleteById(Long id);
}
