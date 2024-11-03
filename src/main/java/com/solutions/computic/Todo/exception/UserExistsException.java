package com.solutions.computic.Todo.exception;

public class UserExistsException extends RuntimeException {
    
    public UserExistsException(String message) { super(message); }
}
