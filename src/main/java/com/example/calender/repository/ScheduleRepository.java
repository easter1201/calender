package com.example.calender.repository;

import com.example.calender.entity.Schedule;

import java.time.*;
import java.util.*;

public interface ScheduleRepository{
    Schedule saveSchedule(Schedule schedule, String userName, String password, String email);
    List<Schedule> findAll();
    List<Schedule> findByFilter(Long userId, LocalDate date);
    Optional<Schedule> findById(Long id);

    void deleteSchedule(Schedule schedule);

    void userExist(Long userId, String userName, String password, String email);

    void updateSchedule(Schedule schedule);

    boolean checkPassword(Long userId, String password);

    List<Schedule> findPaged(int set, int size);

    List<Schedule> findFilteredPage(Long userId, LocalDate date, int set, int size);
    long countAll();
    long countByFilter(Long userId, LocalDate date);
}