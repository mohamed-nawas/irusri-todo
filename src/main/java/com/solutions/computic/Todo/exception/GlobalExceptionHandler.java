package com.solutions.computic.Todo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.solutions.computic.Todo.enums.ErrorResponseStatusType;
import com.solutions.computic.Todo.utils.ResponseGenerator;
import com.solutions.computic.Todo.wrapper.ResponseWrapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseGenerator responseGenerator;
    
    @ExceptionHandler(TodoAppBaseException.class)
    public ResponseEntity<ResponseWrapper> handleTodoAppBaseException(TodoAppBaseException e) {
        log.error("Error occurred, cause => " + e.getMessage(), e);
        return responseGenerator.generateErrorResponse(ErrorResponseStatusType.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ResponseWrapper> handleUserExistsException(UserExistsException e) {
        log.error("Error occurred, cause => " + e.getMessage(), e);
        return responseGenerator.generateErrorResponse(ErrorResponseStatusType.USER_EXISTS);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleUserNotFoundException(UserNotFoundException e) {
        log.error("Error occurred, cause => " + e.getMessage(), e);
        return responseGenerator.generateErrorResponse(ErrorResponseStatusType.USER_NOT_FOUND);
    }
    
    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleTodoNotFoundException(TodoNotFoundException e) {
        log.error("Error occurred, cause => " + e.getMessage(), e);
        return responseGenerator.generateErrorResponse(ErrorResponseStatusType.TODO_NOT_FOUND);
    }
    
    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ResponseWrapper> handlePermissionDeniedException(PermissionDeniedException e) {
        log.error("Error occurred, cause => " + e.getMessage(), e);
        return responseGenerator.generateErrorResponse(ErrorResponseStatusType.FORBIDDEN);
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("Error occurred, cause => " + e.getMessage(), e);
        return responseGenerator.generateErrorResponse(ErrorResponseStatusType.USER_NOT_FOUND);
    }
}
