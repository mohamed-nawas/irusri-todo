package com.solutions.computic.Todo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatusType {
    
    SUCCESS("API request successfull"),
    ERROR("API request error");

    private final String description;
}
