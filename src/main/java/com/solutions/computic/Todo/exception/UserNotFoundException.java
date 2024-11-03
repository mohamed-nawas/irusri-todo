package com.solutions.computic.Todo.exception;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) { super(message); }
}
