package com.example.calender.entity;

import lombok.*;
public class User {
    private String userId;
    private String email;
    private String password;

    public User(String userId, String email, String password){
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(){
        this.password = password;
    }
}
