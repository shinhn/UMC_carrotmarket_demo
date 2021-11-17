package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.item.model.GetItemRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ItemController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ItemProvider itemProvider;
    @Autowired
    private final ItemService itemService;
    @Autowired
    private final JwtService jwtService;

    public ItemController(ItemProvider itemProvider, ItemService itemService, JwtService jwtService){
        this.itemProvider = itemProvider;
        this.itemService = itemService;
        this.jwtService = jwtService;
    }

    // 모든 상품 조회 API
    // [GET] /app/item
    @ResponseBody
    @GetMapping("/app/item")
    public BaseResponse<List<GetItemRes>> getItems(@RequestParam(required = false) String name) {
        try {
            if (name == null) {
                List<GetItemRes> getItemRes = itemProvider.getItems();
                return new BaseResponse<>(getItemRes);
            }
            List<GetItemRes> getItemRes = itemProvider.getItemByName(name);
            return new BaseResponse<>(getItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 상품 등록 API
    // [POST] /app/item/upload
    @ResponseBody
    @PostMapping("/app/item/upload")

}
