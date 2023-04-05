package com.djimenez.items.controllers;

import com.djimenez.items.models.Item;
import com.djimenez.items.models.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    @Qualifier("feignService")
    ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getItems(){
        return new ResponseEntity<List<Item>>(itemService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id){
        return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
    }

}
