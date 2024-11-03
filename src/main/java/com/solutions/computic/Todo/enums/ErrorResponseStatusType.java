package com.solutions.computic.Todo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorResponseStatusType {
    
    MISSING_REQUIRED_FIELDS(400, "Fields required to process the request are missing"),
    UNAUTHORIZED(401, "You are not authorized to access this resource"),
    CREDENTIALS_UNMATCH(401, "Provided login credentials dont match"),
    FORBIDDEN(403, "Your are not authorize to perform this action"),
    USER_NOT_FOUND(404, "User for given email not found"),
    TODO_NOT_FOUND(404, "Todo item not found"),
    USER_EXISTS(409, "User with given identity already exists"),
    INTERNAL_SERVER_ERROR(500, "Internel Server Error");

    private final int code;
    private final String message;
}
