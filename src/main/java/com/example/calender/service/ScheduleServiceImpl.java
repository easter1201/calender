package com.example.calender.service;

import com.example.calender.dto.*;
import com.example.calender.entity.*;
import com.example.calender.exception.*;
import com.example.calender.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.*;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository){
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ScheduleResponse saveSchedule(ScheduleRequest scheduleRequest){
        User user = userRepository.findByUserName(scheduleRequest.getUserName());
        if(user == null){
            System.out.println("신규 등록: " + scheduleRequest.getUserName() + scheduleRequest.getEmail() + scheduleRequest.getPassword());
            user = new User(scheduleRequest.getUserName(), scheduleRequest.getEmail(), scheduleRequest.getPassword());
            System.out.println("생성 객체: " + user.getUserName() + user.getEmail() + user.getPassword());
            userRepository.saveUser(user);
        }
        System.out.println("userId : " + user.getUserId());
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(scheduleRequest.getContent(), user.getUserId(), now);

        Schedule savedSchedule = scheduleRepository.saveSchedule(schedule, scheduleRequest.getUserName(), scheduleRequest.getPassword(), scheduleRequest.getEmail());

        String userName = userRepository.findUserNameById(savedSchedule.getUserId());
        return new ScheduleResponse(savedSchedule, userName);
    }

    @Override
    public List<ScheduleResponse> getAllSchedules(){
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(schedule -> {
            User user = userRepository.findById(schedule.getUserId());
            String userName = (user != null) ? user.getUserName() : "Unknown";
            return new ScheduleResponse(schedule, userName);
        }).collect(Collectors.toList());
    }

    @Override
    public ScheduleResponse getScheduleById(Long id){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("찾을 수 없는 일정입니다."));

        String userName = userRepository.findUserNameById(schedule.getUserId());
        return new ScheduleResponse(schedule, userName);
    }

    @Override
    public void deleteSchedule(Long id, String password){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("제거할 수 없습니다."));

        boolean isCorrect = scheduleRepository.checkPassword(schedule.getUserId(), password);
        if(!isCorrect){
            throw new InvalidPasswordException("비밀번호 불일치");
        }
        scheduleRepository.deleteSchedule(schedule);
    }

    @Override
    public List<ScheduleResponse> getFilteredSchedules(Long userId, LocalDate date){
        List<Schedule> schedules = scheduleRepository.findByFilter(userId, date);
        return schedules.stream().map(schedule -> {
            User user = userRepository.findById(schedule.getUserId());
            String userName = (user != null) ? user.getUserName() : "Unknown";
            return new ScheduleResponse(schedule, userName);
        }).collect(Collectors.toList());
    }

    @Override
    public ScheduleResponse updateSchedule(Long contentId, String newContent, String newUserName, String password){
        Schedule schedule = scheduleRepository.findById(contentId)
                .orElseThrow(() -> new ScheduleNotFoundException("일정 없음"));

        User existingUser = userRepository.findById(schedule.getUserId());
        if(existingUser == null || !existingUser.getPassword().equals(password)){
            throw new InvalidPasswordException("비밀번호 불일치");
        }

        schedule.setContent(newContent);
        schedule.setModifiedTime(LocalDateTime.now());

        scheduleRepository.updateSchedule(schedule);

        userRepository.updateUserName(schedule.getUserId(), newUserName);

        return new ScheduleResponse(schedule, newUserName);
    }

    @Override
    public PagedScheduleResponse getAllSchedulesPaged(int page, int size){
        int set = page * size;
        List<Schedule> schedules = scheduleRepository.findPaged(set, size);
        long totalCnt = scheduleRepository.countAll();

        if(schedules.isEmpty()) throw new ScheduleNotFoundException("일정 없음");

        List<ScheduleResponse> responses = new ArrayList<>();
        for(Schedule schedule : schedules){
            User user = userRepository.findById(schedule.getUserId());
            if(user == null) throw new ScheduleNotFoundException("정보 없음");

            String userName = (user != null) ? user.getUserName() : "Unknown";
            responses.add(new ScheduleResponse(schedule, userName));
        }
        return new PagedScheduleResponse(page, size, totalCnt, responses);
    }

    @Override
    public PagedScheduleResponse getFilteredSchedulesPaged(Long userId, LocalDate date, int page, int size){
        int set = page * size;
        List<Schedule> schedules = scheduleRepository.findFilteredPage(userId, date, set, size);
        long totalCnt = scheduleRepository.countByFilter(userId, date);

        if(schedules.isEmpty()) throw new ScheduleNotFoundException("일정 없음");

        List<ScheduleResponse> responses = new ArrayList<>();

        for(Schedule schedule : schedules){
                User user = userRepository.findById(schedule.getUserId());
                if(user == null) throw new ScheduleNotFoundException("정보 없음");
                String userName = (user != null) ? user.getUserName() : "Unknown";
                responses.add(new ScheduleResponse(schedule, userName));
        }
        return new PagedScheduleResponse(page, size, totalCnt, responses);
    }
}
