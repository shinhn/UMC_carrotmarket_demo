package com.example.demo.src.item.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PostItemReq {
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
