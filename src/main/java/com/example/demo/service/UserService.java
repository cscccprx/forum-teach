package com.example.demo.service;


import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

// 具体业务逻辑
@Service
public class UserService {

    @Resource
    UserMapper userMapper;


    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setToken(user.getToken());
            dbUser.setGmtModified(System.currentTimeMillis());
            userMapper.update(dbUser);
        }
    }

    public String getRes() {
        return "aaaa";
    }


    public User getUserFromDataBase() {
        // 需要和数据库交互
        List<User> all = userMapper.findAll();
        return all.get(0);
    }

    public User createOrUpdate() {
        List<User> all = userMapper.findAll();
        return all.get(0);
    }

    public User findOne(Long id) {
        User user = userMapper.findById(id);
        return user;
    }

    public User getOne() {
        User user = userMapper.findByAccountId("1");
        return user;
    }

    public void insert(User user) {
        userMapper.insert(user);
    }

    public void update(User us) {
        userMapper.update(us);
    }

    public void delete(User user) {
        userMapper.delete(user);
    }
}
