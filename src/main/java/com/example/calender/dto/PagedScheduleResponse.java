package com.example.calender.dto;

import lombok.*;
import java.util.*;

@Getter
@Setter
public class PagedScheduleResponse {
    private int page;
    private int size;
    private long elements;
    private List<ScheduleResponse> schedules;

    public PagedScheduleResponse(int page, int size, long elements, List<ScheduleResponse> schedules){ //페이지네이션
        this.page = page;
        this.size = size;
        this.elements = elements;
        this.schedules = schedules;
    }
}
