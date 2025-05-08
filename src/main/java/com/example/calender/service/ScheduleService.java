package com.example.calender.service;

import com.example.calender.entity.Schedule;
import com.example.calender.dto.*;
import java.util.*;

public interface ScheduleService {
    ScheduleResponse saveSchedule(ScheduleRequest scheduleRequest);

    List<ScheduleResponse> getAllSchedules();

    ScheduleResponse getScheduleById(Long id);

    //ScheduleResponse updateSchedule(Long id, String task, String userName, String password);

    //ScheduleResponse updateTask(Long id, String task);

    void deleteSchedule(Long id);
}
