package com.example.calender.service;

import com.example.calender.entity.Schedule;
import com.example.calender.dto.*;

import java.time.LocalDate;
import java.util.*;

public interface ScheduleService {
    ScheduleResponse saveSchedule(ScheduleRequest scheduleRequest);

    List<ScheduleResponse> getAllSchedules();

    List<ScheduleResponse> getFilteredSchedules(Long userId, LocalDate date);

    ScheduleResponse getScheduleById(Long id);

    ScheduleResponse updateSchedule(Long contentId, String newContent, String newUserName, String password);

    void deleteSchedule(Long id, String password);

    PagedScheduleResponse getAllSchedulesPaged(int page, int size);

    PagedScheduleResponse getFilteredSchedulesPaged(Long userId, LocalDate date, int page, int size);
}
