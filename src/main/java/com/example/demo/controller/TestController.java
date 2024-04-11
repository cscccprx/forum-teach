package com.example.demo.controller;

import com.example.demo.dto.TestDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class TestController {

    @Autowired
    UserService userService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/asd")
    @ResponseBody
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println(name);
        return "asddddd";
    }

//    @PostMapping("/post")
//    public User post(@RequestBody User user) {
//        User orUpdate = userService.createOrUpdate();
//        return orUpdate;
//    }

    @GetMapping("/post")
    @ResponseBody
    public User post() {
        User orUpdate = userService.createOrUpdate();
//        String res = userService.getRes();
//        User orUpdate = userService.getOne();
        return orUpdate;
    }

    // 需求： 我需要从数据库中查询数据   现有连接串 -》 库 -》 表
    // url : /xxx
    @GetMapping("/xxx")
    @ResponseBody
    public User postTest(){
        User userFromDataBase = userService.getUserFromDataBase();
        return userFromDataBase;
    }
    // spring boot + mybatis mysql
    // 1. 熟悉数据库 增 删 改 查
    // 2. 自己建 数据库的 库 和 一个表
    // 3. 服务怎么访问数据库 // 作业就是



    @GetMapping("/greeti")
    public String hello(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    // 测试ajax
    @GetMapping("/test")
    public String testing(Model model) {
        String name = "sdsadasdasdas";
        model.addAttribute("name", name);
        return "test";
    }

    @PostMapping("/comment")
    @ResponseBody
    public User postTest(@RequestBody TestDTO testDTO) {
        System.out.println(testDTO);
        System.out.println();
        User orUpdate = userService.findOne(testDTO.getId());

        return orUpdate;
    }

    @PostMapping("/mybatis")
    @ResponseBody
    public User mybatisTest(@RequestBody User user) {
//        System.out.println(testDTO);
//        System.out.println();
//        User user = new User();
//        user.setName(testDTO.getName());
        // 新增
        userService.insert(user);
        // 更新
//        user.setId(22l);
//        userService.update(user);
        // 删除
//        user.setId(22l);
//        userService.delete(user);
        System.out.println("success");
        User orUpdate = userService.createOrUpdate();
//        String res = userService.getRes();
//        User orUpdate = userService.getOne();
        return orUpdate;
    }


}
