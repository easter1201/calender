package com.example.calender.exception;

public class ScheduleNotFoundException extends RuntimeException{
    public ScheduleNotFoundException(String message){
        super(message);
    }
}
