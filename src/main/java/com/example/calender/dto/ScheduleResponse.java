package com.example.calender.dto;

import com.example.calender.entity.Schedule;
import java.time.*;
import lombok.*;

@Getter
public class ScheduleResponse {
    private Long contentId;

    private String content;

    private String userId;

    private LocalDateTime generatedTime;

    private LocalDateTime modifiedTime;

    public ScheduleResponse(Schedule schedule){
        this.contentId = schedule.getContentId();
        this.content = schedule.getContent();
        this.userId = schedule.getUserId();
        this.generatedTime = schedule.getGeneratedTime();
        this.modifiedTime = schedule.getModifiedTime();
    }
}
