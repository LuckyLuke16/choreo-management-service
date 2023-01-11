package com.example.managementservice.controller.controllerInterfaces;

import com.example.managementservice.model.ShoppingCartItemDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/shopping-cart")
public interface ShoppingCartOperations {

    @GetMapping
    List<ShoppingCartItemDTO> fetchShoppingCartContent();

    @DeleteMapping("/{itemId}")
    void deleteItem(@PathVariable int itemId);

    @PostMapping("/{itemId}")
    void addItem(@PathVariable int itemId);
}
