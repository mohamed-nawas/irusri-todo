package com.solutions.computic.Todo.exception;

public class TodoAppBaseException extends RuntimeException {
    
    public TodoAppBaseException(String message) { super(message); }
    public TodoAppBaseException(String message, Throwable error) { super(message, error); }
}
