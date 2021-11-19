package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.src.item.model.GetItemRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service

public class ItemProvider {
    private final ItemDao itemDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public ItemProvider(ItemDao itemDao, JwtService jwtService) {
        this.itemDao = itemDao;
        this.jwtService = jwtService;
    }

    // 모든 상품 조회
    public List<GetItemRes> getItems() throws BaseException {
        try {
            List<GetItemRes> getItemRes = itemDao.getItems();
            return getItemRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 name을 갖는 상품 정보 조회
    public List<GetItemRes> getItemByName(String name) throws BaseException {
        try {
            List<GetItemRes> getItemRes = itemDao.getItemByName(name);
            return getItemRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 status를 갖는 상품 정보 조회 (판매중, 판매완료)
    public List<GetItemRes> getItemByStatus(String status) throws BaseException {
        try {
            List<GetItemRes> getItemRes = itemDao.getItemByStatus(status);
            return getItemRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
