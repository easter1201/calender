package com.example.calender.exception;

import lombok.*;

@Getter
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message){
        this.message = message;
    }

}
