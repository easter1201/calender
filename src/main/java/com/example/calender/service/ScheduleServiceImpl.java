package com.example.calender.service;

import com.example.calender.dto.*;
import com.example.calender.entity.Schedule;
import com.example.calender.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.*;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponse saveSchedule(ScheduleRequest scheduleRequest){
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(scheduleRequest.getContent(), scheduleRequest.getUserId(), now);

        Schedule savedSchedule = scheduleRepository.saveSchedule(schedule);

        return new ScheduleResponse(savedSchedule);
    }

    @Override
    public List<ScheduleResponse> getAllSchedules(){
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(ScheduleResponse::new).collect(Collectors.toList());
    }

    @Override
    public ScheduleResponse getScheduleById(Long id){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 일정입니다."));
        return new ScheduleResponse(schedule);
    }

    /*@Override
    public ScheduleResponse updateSchedule(Long id, String task, String userName, String password){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수정할 수 없습니다."));
        schedule.update(task, userName, password);
        Schedule updatedSchedule = scheduleRepository.saveSchedule(schedule);

        return new ScheduleResponse(updatedSchedule);
    }*/

    @Override
    public void deleteSchedule(Long id){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("제거할 수 없습니다."));

        scheduleRepository.deleteSchedule(schedule);
    }
}
