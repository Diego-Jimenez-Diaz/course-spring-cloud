package com.djimenez.items.models.service.impl;

import com.djimenez.items.models.Item;
import com.djimenez.items.models.Product;
import com.djimenez.items.models.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("restTemplateService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Item> getAll() {
        List<Product> productList = Arrays.asList(restTemplate.getForObject("http://products-service/products", Product[].class));
        return productList.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        Product product = restTemplate.getForObject("http://products-service/products/{id}", Product.class, pathVariables);
        return new Item(product, 1);
    }
}
