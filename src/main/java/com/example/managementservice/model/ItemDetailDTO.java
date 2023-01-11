package com.example.managementservice.model;

import lombok.Data;

@Data
public class ItemDetailDTO {

    private int id;

    private String name;

    private String description;

    private String genre;

    private String author;

    private float price;

    private int quantity;
}
