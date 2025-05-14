package com.example.calender.entity;

import java.time.*;
import lombok.*;

@Getter
@Setter
public class Schedule {
    private Long contentId;
    private String content;
    private Long userId;
    private LocalDateTime generatedTime;
    private LocalDateTime modifiedTime;
    private String userName;

    public Schedule(String content, Long userId, LocalDateTime generatedTime){
        this.content = content;
        this.userId = userId;
        this.generatedTime = generatedTime;
        this.modifiedTime = generatedTime;
    }

    public Schedule(Long contentId, String content, Long userId, LocalDateTime generatedTime, LocalDateTime modifiedTime){
        this.contentId = contentId;
        this.content = content;
        this.userId = userId;
        this.generatedTime = generatedTime;
        this.modifiedTime = modifiedTime;
    }
}
