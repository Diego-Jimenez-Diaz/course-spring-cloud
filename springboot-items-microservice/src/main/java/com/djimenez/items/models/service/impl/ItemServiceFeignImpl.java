package com.djimenez.items.models.service.impl;

import com.djimenez.items.clients.ProductFeignClient;
import com.djimenez.items.models.Item;
import com.djimenez.items.models.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("feignService")
public class ItemServiceFeignImpl implements ItemService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public List<Item> getAll() {
        return productFeignClient.getAll().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id) {
        // TODO Auto-generated method stub
        return new Item(productFeignClient.getById(id),1);
    }
}
