package com.djimenez.products.models.dao;

import com.djimenez.products.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Long> {
}
