package com.solutions.computic.Todo.wrapper;

import com.solutions.computic.Todo.domain.dto.ResponseDto;
import com.solutions.computic.Todo.enums.ResponseStatusType;

import lombok.Getter;

@Getter
public class SuccessResponseWrapper extends ResponseWrapper {
    
    private final ResponseDto data;

    public SuccessResponseWrapper(ResponseDto data, String message, int code) {
        super(ResponseStatusType.SUCCESS, message, code);
        this.data = data;
    }
}
