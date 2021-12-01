package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.item.model.*;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

// CORS 적용
@CrossOrigin("*") // 모든 요청에 대해 접근을 허용하기 때문에 보안적으로 절대 좋은 방법이 아님

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

    // 특정 이름 상품 조회 API
    // [GET] /app/item?name=
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
    public BaseResponse<PostItemRes> uploadItem(@RequestBody PostItemReq postItemReq) {
        try {
            PostItemRes postItemRes = itemService.uploadItem(postItemReq);
            return new BaseResponse<>(postItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

     // 상품 가격 변경 API
     // [PATCH] /item/:itemIndex
    @ResponseBody
    @PatchMapping("app/item/{itemIndex}")
    public BaseResponse<String> modifyItemPrice(@PathVariable("itemIndex") int itemIndex, @RequestBody Item item) {
        try {
            PatchItemReq patchItemReq = new PatchItemReq(itemIndex, item.getPrice());
            itemService.modifyItemPrice(patchItemReq);

            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 상품 상태(status) 변경 API (판매완료, 판매중 등)
    // [PATCH] /item/status/:itemIndex
    @ResponseBody
    @PatchMapping("app/item/status/{itemIndex}")
    public BaseResponse<String> modifyItemStatus(@PathVariable("itemIndex") int itemIndex, @RequestBody Item item) {
        try {
            PatchItemStatusReq patchItemStatusReq = new PatchItemStatusReq(itemIndex, item.getStatus());
            itemService.modifyItemStatus(patchItemStatusReq);

            String result = "상품의 상태정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 특정 상태(판매중 or 판매완료) 상품 조회 API
    // [GET] /app/item?status=
    @ResponseBody
    @GetMapping("/app/item-status")
    public BaseResponse<List<GetItemRes>> getItemByStatus(@RequestParam(required = false) String status) {
        try {
            List<GetItemRes> getItemRes = itemProvider.getItemByStatus(status);
            return new BaseResponse<>(getItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
