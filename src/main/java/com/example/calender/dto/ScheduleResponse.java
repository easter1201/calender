package com.example.calender.dto;

import com.example.calender.entity.Schedule;
import java.time.*;
import lombok.*;

@Getter
public class ScheduleResponse {
    private Long id;

    private String task;

    private String userName;

    private LocalDateTime generatedTime;

    private LocalDateTime modifiedTime;

    public ScheduleResponse(Schedule schedule){
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.userName = schedule.getUserName();
        this.generatedTime = schedule.getGeneratedTime();
        this.modifiedTime = schedule.getModifiedTime();
    }
}
