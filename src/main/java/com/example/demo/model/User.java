package com.example.demo.model;


import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String accountId;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
    private String bio;

}
/**
 * url :wwwwasdsadsadsad
 * GET / POST
 * data:{}  // json
 */
