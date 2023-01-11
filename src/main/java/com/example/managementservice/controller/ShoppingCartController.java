package com.example.managementservice.controller;

import com.example.managementservice.controller.controllerInterfaces.ShoppingCartOperations;
import com.example.managementservice.model.ShoppingCartItemDTO;
import com.example.managementservice.model.UserId;
import com.example.managementservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ShoppingCartController implements ShoppingCartOperations {

    private ShoppingCartService shoppingCartService;

    private final UserId userId;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserId userId) {
        this.shoppingCartService = shoppingCartService;
        this.userId = userId;
    }

    public List<ShoppingCartItemDTO> fetchShoppingCartContent() {
        List<ShoppingCartItemDTO> allShoppingCartItems;
        try {
            String id = userId.getUserId();
            allShoppingCartItems = shoppingCartService.fetchShoppingCartItems(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return allShoppingCartItems;
    }

    public void deleteItem(int itemId) {
        try {
            String id = userId.getUserId();
            shoppingCartService.deleteShoppingCartItem(itemId, id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void addItem(int itemId) {
        try {
            String id = userId.getUserId();
            shoppingCartService.addShoppingCartItem(itemId, id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
