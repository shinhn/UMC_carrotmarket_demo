package com.example.demo.src.item.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PatchItemReq {
    private int itemIndex;
    private String price;
}
