package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void save(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> viewShoppingCart();

    void cleanAll();

    void deleteOne(ShoppingCartDTO shoppingCartDTO);
}
