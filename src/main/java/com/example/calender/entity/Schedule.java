package com.example.calender.entity;

import java.time.*;
import lombok.*;

@Getter
public class Schedule {
    private Long contentId;
    private String content;
    private String userId;
    private LocalDateTime generatedTime;
    private LocalDateTime modifiedTime;

    public Schedule(String content, String userId, LocalDateTime generatedTime){
        this.content = content;
        this.userId = userId;
        this.generatedTime = generatedTime;
        this.modifiedTime = generatedTime;
    }

    public Schedule(Long contentId, String content, String userId, LocalDateTime generatedTime, LocalDateTime modifiedTime){
        this.contentId = contentId;
        this.content = content;
        this.userId = userId;
        this.generatedTime = generatedTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getContentId(){
        return contentId;
    }

    public void setContentId(Long contentId){
        this.contentId = contentId;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public LocalDateTime getGeneratedTime(){
        return generatedTime;
    }

    public void setGeneratedTime(LocalDateTime generatedTime){
        this.generatedTime = generatedTime;
    }

    public LocalDateTime getModifiedTime(){
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime){
        this.modifiedTime = modifiedTime;
    }

    public void update(String content, String userId){
        this.content = content;
        this.userId = userId;
        this.modifiedTime = LocalDateTime.now();
    }
}
