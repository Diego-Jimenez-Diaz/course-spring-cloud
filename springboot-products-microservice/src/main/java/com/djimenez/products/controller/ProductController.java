package com.djimenez.products.controller;

import com.djimenez.products.models.entity.Product;
import com.djimenez.products.models.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Value("${server.port}")
	private Integer port;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAll().stream().map(product -> {
        	//product.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
        	product.setPort(port);
        	return product;
        }).collect(Collectors.toList()) , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
    	Product product = productService.findById(id); 
    	//product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
    	product.setPort(port);
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }
    
}
