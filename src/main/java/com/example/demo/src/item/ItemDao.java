package com.example.demo.src.item;

import com.example.demo.src.item.model.GetItemRes;
import com.example.demo.src.user.model.GetUserRes;
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

    // 해당 name을 갖는 상품의 정보 조회
    public List<GetItemRes> getItemByName(String name) {
        String getUsersByNameQuery = "select * from Item where name =?";
        String getUsersByNameParams = name;

        return this.jdbcTemplate.query(getUsersByNameQuery,
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
                getUsersByNameParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
}