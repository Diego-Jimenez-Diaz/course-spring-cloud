package com.djimenez.items.models.service;

import com.djimenez.items.models.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();
    Item findById(Long id);
}
