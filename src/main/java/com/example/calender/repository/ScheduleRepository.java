package com.example.calender.repository;

import com.example.calender.entity.Schedule;
import java.util.*;

public interface ScheduleRepository{
    Schedule saveSchedule(Schedule schedule);
    List<Schedule> findAll();
    Optional<Schedule> findById(Long id);

    void deleteSchedule(Schedule schedule);
}