package com.example.demo.dto;

import lombok.Data;

@Data
public class GithubUserDTO {
    private String name;
    private long id;
    private String bio;
    private String avatar_url;

}
