package com.example.managementservice.controller.controllerInterfaces;

import com.example.managementservice.model.ItemDTO;
import com.example.managementservice.model.ItemDetailDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/items")
public interface ProductOperations {

    @GetMapping
    List<ItemDTO> fetchItemsByCategory(@RequestParam(defaultValue = "ALL") String genre);

    @GetMapping("/{itemID}")
    ItemDetailDTO fetchSingleItem(@PathVariable int itemID);

}
