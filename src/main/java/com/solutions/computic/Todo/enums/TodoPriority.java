package com.solutions.computic.Todo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TodoPriority {
    
    LOW("Low priority"),
    MEDIUM("Medium priority"),
    HIGH("High priority");

    private final String description;
}
