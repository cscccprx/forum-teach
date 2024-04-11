package com.example.demo.controller;


import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUserDTO;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * prx
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Autowired
    UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String secretId;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(secretId);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        //得到githubUser
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        String[] strings = accessToken.split("&");
        String[] strings1= strings[0].split("=");
        accessToken = strings1[1];
        GithubUserDTO githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser);
        if (githubUser != null){
            //登陆成功，存入数据库
            request.getSession().setAttribute("user",githubUser);
//            User user= new User();
//            String token = UUID.randomUUID().toString();
//            user.setToken(token);
//            user.setAccountId(String.valueOf(githubUser.getId()));
//            user.setName(githubUser.getName());
//            user.setAvatarUrl(githubUser.getAvatar_url());
//            userService.createOrUpdate(user);
//            response.addCookie(new Cookie("token",token));
            return "redirect:/greeting";
        }else{
            log.error("get github error, {}",githubUser);
            return "redirect:/greeting";
        }

    }

//    @GetMapping("/callback")
//    public String callback(@RequestParam("code") String code,
//                           @RequestParam("state") String state,
//                           HttpServletRequest request,
//                           HttpServletResponse response) {
//        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
//        accessTokenDTO.setClient_id(clientId);
//        accessTokenDTO.setClient_secret(secretId);
//        accessTokenDTO.setRedirect_uri(redirectUri);
//        accessTokenDTO.setState(state);
//        accessTokenDTO.setCode(code);
//        //得到githubUser
//        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        System.out.println(accessToken);
//        String[] strings = accessToken.split("&");
//        String[] strings1= strings[0].split("=");
//        accessToken = strings1[1];
//        GithubUserDTO githubUser = githubProvider.getUser(accessToken);
//        System.out.println(githubUser);
//        if (githubUser != null){
//            //登陆成功，存入数据库
//            User user= new User();
//            String token = UUID.randomUUID().toString();
//            user.setToken(token);
//            user.setAccountId(String.valueOf(githubUser.getId()));
//            user.setName(githubUser.getName());
//            user.setAvatarUrl(githubUser.getAvatar_url());
//            userService.createOrUpdate(user);
//            response.addCookie(new Cookie("token",token));
//            return "redirect:/";
//        }else{
//            log.error("get github error, {}",githubUser);
//            return "redirect:/";
//        }
//
//    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }

}
