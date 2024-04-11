package com.example.demo.provider;

import com.alibaba.fastjson.JSON;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUserDTO;
import okhttp3.*;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println("asd");
            System.out.println(string);
            return string;
        } catch (IOException e) {
            System.out.println("fail");
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDTO getUser(String accesstoken){
//        MediaType mediaType = MediaType.get("application/json; charset=utf-8;Authorization: Bearer OAUTH-TOKEN");

        OkHttpClient client = new OkHttpClient();
        System.out.println(accesstoken);
        String value = "Bearer " + accesstoken;
        Headers authorization = new Headers.Builder().add("Authorization", value).build();
        Request request = new Request.Builder()
                .url(" https://api.github.com/user")
                .headers(authorization)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUserDTO githubUserDTO = JSON.parseObject(string, GithubUserDTO.class);
            System.out.println("用户信息"+githubUserDTO);
            return githubUserDTO;
        } catch (IOException e) {

        }
        return null;
    }



}

