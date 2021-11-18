package com.example.demo.src.item.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PatchItemStatusReq {
    private int itemIndex;
    private String status;
}
