package com.djimenez.items.controllers;

import com.djimenez.items.models.Item;
import com.djimenez.items.models.Product;
import com.djimenez.items.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    @Qualifier("feignService")
    ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getItems(){
        return new ResponseEntity<List<Item>>(itemService.getAll(), HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "getByIdAlternativeMethod")
    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id){
        return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
    }

    public ResponseEntity<Item> getByIdAlternativeMethod(Long id){
    	
    	Item item = new Item();
    	Product product =new Product();
    	
    	product.setId(1L);
    	product.setName("sony Camera");
    	product.setPrice(1000.0);
    	product.setCreateAt(LocalDate.now());
    	product.setPort(null);
    	
    	item.setProduct(product);
    	item.setQuantity(1);
    	
    	return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
