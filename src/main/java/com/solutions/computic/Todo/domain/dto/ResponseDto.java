package com.solutions.computic.Todo.domain.dto;

/**
 * All the responses should inherit this interface for generalizing responses.
 * All the response specific validations should be defined in this interface
 */
public interface ResponseDto extends BaseDto {
    
    /**
     * This method is used to convert the response objects into json string for logging in debugging purposes
     * Here, PII(Personal Identification Information) should be avoided being logged as a best practice
     * @return Json string
     */
    String toLogJson();
}
