package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.item.model.PostItemReq;
import com.example.demo.src.item.model.PostItemRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service

public class ItemService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ItemDao itemDao;
    private final ItemProvider itemProvider;
    private final JwtService jwtService;


    @Autowired //readme 참고
    public ItemService(ItemDao itemDao, ItemProvider itemProvider, JwtService jwtService) {
        this.itemDao = itemDao;
        this.itemProvider = itemProvider;
        this.jwtService = jwtService;

    }

    // 상품등록 (Post)
    public PostItemRes uploadItem(PostItemReq postItemReq) throws BaseException {
        try {
            int itemIndex = itemDao.uploadItem(postItemReq);
            return new PostItemRes(itemIndex);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
