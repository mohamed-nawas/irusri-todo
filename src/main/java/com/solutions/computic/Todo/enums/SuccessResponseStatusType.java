package com.solutions.computic.Todo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessResponseStatusType {
    
    REGISTER(201, "Successfully created the user"),
    CREATE_TOOD(201, "Successfully created the todo item"),
    GET_TODO(200, "Successfully returned the todo items"),
    UPDATE_TODO(200, "Successfully update the todo item"),
    DELETE_TODO(200, "Successfully deleted the todo item"),
    LOGIN(200, "Successfully logged in the user");

    private final int code;
    private final String message;
}
