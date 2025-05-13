package com.example.calender.controller;

import com.example.calender.service.*;
import com.example.calender.dto.*;
import com.example.calender.repository.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest scheduleRequest){
        ScheduleResponse createdSchedule = scheduleService.saveSchedule(scheduleRequest);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getScheduleById(@PathVariable Long id){
        ScheduleResponse schedule = scheduleService.getScheduleById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody PasswordRequest request){
        scheduleService.deleteSchedule(id, request.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<PagedScheduleResponse> getSchedules(@RequestParam(required = false) Long userId, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(userId == null && date == null){
            return ResponseEntity.ok(scheduleService.getAllSchedulesPaged(page, size));
        }
        else return ResponseEntity.ok(scheduleService.getFilteredSchedulesPaged(userId, date, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable("id") Long contentId, @RequestBody ScheduleUpdateRequest request){
        ScheduleResponse updatedSchedule = scheduleService.updateSchedule(contentId, request.getContent(), request.getUserName(), request.getPassword());
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }
}
