package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Item {
    private int itemIndex;
    private String name;
    private String userName;
    private String location;
    private String itemImgUrl;
    private String date;
    private String description;
    private String price;
    private int views;
    private int likes;
}
