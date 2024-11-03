package com.solutions.computic.Todo.wrapper;

import com.solutions.computic.Todo.enums.ResponseStatusType;

public class ErrorResponseWrapper extends ResponseWrapper {
    
    public ErrorResponseWrapper(String message, int code) {
        super(ResponseStatusType.ERROR, message, code);
    }
}
