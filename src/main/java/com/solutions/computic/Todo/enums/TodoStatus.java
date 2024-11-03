package com.solutions.computic.Todo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TodoStatus {
    
    PENDING("Pending"),
    PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String description;
}
