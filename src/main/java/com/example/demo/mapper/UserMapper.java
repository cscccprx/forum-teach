package com.example.demo.mapper;


import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;



@Mapper
public interface UserMapper {

    @Select("select * from user")
    public void getItem();

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values " +
            "(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Select("select * from user")
    List<User> findAll();

    @Update("update user set name = #{name},token = #{token}," +
            "gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);

    @Delete("DELETE FROM user\n" +
            "WHERE id = #{id}")
    void delete(User dbUser);
}

