package com.example.calender.entity;

import lombok.*;

@Getter
@Setter
public class User {
    private Long userId;
    private String userName;
    private String email;
    private String password;

    public User(Long userId,String userName, String email, String password){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
