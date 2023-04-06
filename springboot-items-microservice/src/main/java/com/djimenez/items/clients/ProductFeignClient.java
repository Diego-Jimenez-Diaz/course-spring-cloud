package com.djimenez.items.clients;

import com.djimenez.items.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "products-service")
@RequestMapping("/products")
public interface ProductFeignClient {

    @GetMapping
    public List<Product> getAll();

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id);

}
