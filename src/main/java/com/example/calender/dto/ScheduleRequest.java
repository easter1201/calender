package com.example.calender.dto;

import lombok.*;

@Getter
@Setter
public class ScheduleRequest {
    private String content;
    private String userName;
    private String password;
    private String email;
}
