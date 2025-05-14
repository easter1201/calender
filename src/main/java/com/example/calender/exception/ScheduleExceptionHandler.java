package com.example.calender.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@ControllerAdvice
public class ScheduleExceptionHandler {
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ScheduleNotFoundException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAnotherException(Exception e){
        return new ResponseEntity<>(new ErrorResponse("정의되지 않은 오류"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
