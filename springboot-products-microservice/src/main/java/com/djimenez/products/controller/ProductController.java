package com.djimenez.products.controller;

import com.djimenez.products.models.entity.Product;
import com.djimenez.products.models.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductController {
	
	//Toma el puerto real
	@Autowired
	private Environment environment;
		
	//Toma el puerto del application.prpperties
		
	@Value("${server.port}")
	private Integer port;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAll().stream().map(product -> {
        	product.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
        	//product.setPort(port);
        	return product;
        }).collect(Collectors.toList()) , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) throws InterruptedException {
    	
    	if(id.equals(10L)) {
    		throw new IllegalStateException("Error simuation: Product not found");
    	}
    	
    	if(id.equals(7L)) {
    		TimeUnit.SECONDS.sleep(2L);
    	}
    	Product product = productService.findById(id); 
    	product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
    	//product.setPort(port);
    	
    	//Test Timeout
    	/*try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    	
    	//Change to false to test Hystrix
    	boolean hystrixValidator = true;
    	
    	if(!hystrixValidator) {
    		throw new RuntimeException();
    	}
    	
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }
    
}
