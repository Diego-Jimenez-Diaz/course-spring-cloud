package com.djimenez.items.models;

public class Item {

    private Product product;
    private Integer quantity;

    public Double getTotal() {
        return product.getPrice() * quantity;
    }
}
