package com.djimenez.items.controllers;

import com.djimenez.items.models.Item;
import com.djimenez.items.models.Product;
import com.djimenez.items.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RefreshScope
public class ItemController {

	
	private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    @Qualifier("feignService")
    private ItemService itemService;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;
    
    @Autowired
    private Environment env;
    
    @Value("${configuration.description}")
    private String description;
    
    @GetMapping
    public ResponseEntity<List<Item>> getItems(@RequestParam(name="name", required = false) String name, @RequestHeader(name = "token-request", required = false) String token){
    	log.info(String.format("name: %s", name));
    	log.info(String.format("Token: %s", token));
        return new ResponseEntity<List<Item>>(itemService.getAll(), HttpStatus.OK);
    }

    //@HystrixCommand(fallbackMethod = "getByIdAlternativeMethod")
    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id){
        return circuitBreakerFactory.create("items")
        		.run(()-> new ResponseEntity<>(itemService.findById(id), HttpStatus.OK), e -> getByIdAlternativeMethod(id, e));
    }
    
    
    @CircuitBreaker(name = "items", fallbackMethod = "getByIdAlternativeMethod")
    @GetMapping("v2/{id}")
    public ResponseEntity<Item> findById2(@PathVariable Long id){
        return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
    }
    
    @CircuitBreaker(name = "items", fallbackMethod = "getByIdAlternativeMethod2")
    @TimeLimiter(name = "items")
    @GetMapping("v3/{id}")
    public ResponseEntity<CompletableFuture<Item>> findById3(@PathVariable Long id){
        return new ResponseEntity<>(CompletableFuture.supplyAsync(()->itemService.findById(id)) , HttpStatus.OK);
    }

    public ResponseEntity<Item> getByIdAlternativeMethod(Long id, Throwable e){
    	
    	log.info(e.getMessage());
    	
    	Item item = new Item();
    	Product product =new Product();
    	
    	product.setId(1L);
    	product.setName("Alternative product");
    	product.setPrice(0.0);
    	product.setCreateAt(null);
    	product.setPort(null);
    	
    	item.setProduct(product);
    	item.setQuantity(0);
    	
    	return new ResponseEntity<>(item, HttpStatus.OK);
    }
    
    public ResponseEntity<CompletableFuture<Item>> getByIdAlternativeMethod2(Long id, Throwable e){
    	
    	log.info(e.getMessage());
    	
    	Item item = new Item();
    	Product product =new Product();
    	
    	product.setId(1L);
    	product.setName("Alternative product");
    	product.setPrice(0.0);
    	product.setCreateAt(null);
    	product.setPort(null);
    	
    	item.setProduct(product);
    	item.setQuantity(0);
    	
    	return new ResponseEntity<>(CompletableFuture.supplyAsync(() -> item), HttpStatus.OK);
    }
    
    @GetMapping("/config")
    public ResponseEntity<?> getconfiguration(@Value("${server.port}")String puerto){
    	Map<String, String> json = new HashMap<>();
    	json.put("texto", description);
    	json.put("puerto", puerto);
    	json.put("configuration.env.info", env.getProperty("configuration.env.info"));
    	
    	if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
        	json.put("autor.name", env.getProperty("autor.name"));
        	json.put("autor.email", env.getProperty("autor.email"));
    	}
    	
    	return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
