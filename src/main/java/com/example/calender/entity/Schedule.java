package com.example.calender.entity;

import java.time.*;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@AllArgsConstructor
public class Schedule {
    @Setter
    private Long id;
    @Column(name = "content")
    private String task;
    @Column(name = "userName")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = )
    private LocalDateTime generatedTime;
    private LocalDateTime modifiedTime;

    public Schedule(String task, String userName, String password, LocalDateTime generatedTime, LocalDateTime modifiedTime){
        this.task = task;
        this.userName = userName;
        this.password = password;
        this.generatedTime = generatedTime;
        this.modifiedTime = modifiedTime;
    }

    public void update(String task, String userName, String password){
        this.task = task;
        this.userName = userName;
        this.password = password;
        this.modifiedTime = LocalDateTime.now();
    }
}
