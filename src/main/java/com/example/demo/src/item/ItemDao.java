package com.example.demo.src.item;

import com.example.demo.src.item.model.GetItemRes;
import com.example.demo.src.item.model.PatchItemReq;
import com.example.demo.src.item.model.PatchItemStatusReq;
import com.example.demo.src.item.model.PostItemReq;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository

public class ItemDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Item 테이블에 존재하는 전체 상품 정보 조회 
    public List<GetItemRes> getItems() {
        String getItemQuery = "select * from Item";

        return this.jdbcTemplate.query(getItemQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getInt("itemIndex"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("location"),
                        rs.getString("itemImgUrl"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getString("price"),
                        rs.getInt("views"),
                        rs.getInt("likes")
                        )
        );
    }

    // 상품 정보 조회 (paging)
    public List<GetItemRes> getItems(int pageNum) {

        String getItemQuery = "select * from Item limit 2 offset ?"; // LIMIT: 행을 얼마나 가져올지, OFFSET: 어디서 부터 가져올지
        int getItemParams = (pageNum-1)*2; // offset시작 페이지 = (페이지-1)*페이지당 데이터 수

        return this.jdbcTemplate.query(getItemQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getInt("itemIndex"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("location"),
                        rs.getString("itemImgUrl"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getString("price"),
                        rs.getInt("views"),
                        rs.getInt("likes") // RowMapper : 원하는 결과값 형태로 받기
                ),
                getItemParams
        );
    }

    // 해당 name을 갖는 상품의 정보 조회
    public List<GetItemRes> getItemByName(String name) {
        String getItemByNameQuery = "select * from Item where name =?";
        String getItemByNameParams = name;

        return this.jdbcTemplate.query(getItemByNameQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getInt("itemIndex"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("location"),
                        rs.getString("itemImgUrl"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getString("price"),
                        rs.getInt("views"),
                        rs.getInt("likes")
                ),
                getItemByNameParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // 상품 등록
    public int uploadItem(PostItemReq postItemReq) {
        String uploadItemQuery = "insert into Item2 (name, userName, location, itemImgUrl, date, description, price, views, likes) VALUES (?,?,?,?,?,?,?,?,?)";
        Object[] uploadItemParams = new Object[]{postItemReq.getName(), postItemReq.getUserName(),postItemReq.getLocation(),postItemReq.getItemImgUrl(),postItemReq.getDate(),postItemReq.getDescription(),postItemReq.getPrice(),postItemReq.getViews(),postItemReq.getLikes()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(uploadItemQuery, uploadItemParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // 상품 가격 변경
    public int modifyItemPrice(PatchItemReq patchItemReq) {
        String modifyItemPriceQuery = "update Item set price = ? where itemIndex = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyItemPriceParams = new Object[]{patchItemReq.getPrice(), patchItemReq.getItemIndex()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyItemPriceQuery, modifyItemPriceParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 상품 상태 변경
    public int modifyItemStatus(PatchItemStatusReq patchItemStatusReq) {
        String modifyItemStatusQuery = "update Item set status = ? where itemIndex = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyItemStatusParams = new Object[]{patchItemStatusReq.getStatus(), patchItemStatusReq.getItemIndex()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyItemStatusQuery, modifyItemStatusParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 해당 status를 갖는 상품의 정보 조회 (판매중, 판매완료)
    public List<GetItemRes> getItemByStatus(String status) {
        String getItemByStatusQuery = "select * from Item where status =?";
        String getItemByStatusParams = status;

        return this.jdbcTemplate.query(getItemByStatusQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getInt("itemIndex"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("location"),
                        rs.getString("itemImgUrl"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getString("price"),
                        rs.getInt("views"),
                        rs.getInt("likes")
                ),
                getItemByStatusParams);
    }
}