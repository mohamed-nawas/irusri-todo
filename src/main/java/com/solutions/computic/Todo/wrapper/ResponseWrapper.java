package com.solutions.computic.Todo.wrapper;

import com.solutions.computic.Todo.enums.ResponseStatusType;

import lombok.Getter;

@Getter
public class ResponseWrapper {
    
    private final String status;
    private final String message;
    private final int code;

    protected ResponseWrapper(ResponseStatusType responseStatus, String message, int code) {
        this.status = responseStatus.getDescription();
        this.message = message;
        this.code = code;
    }
}
