package com.example.calender.dto;

import lombok.*;

@Getter
@Setter
public class ScheduleUpdateRequest {
    private String content;
    private String userName;
    private String password;
}
