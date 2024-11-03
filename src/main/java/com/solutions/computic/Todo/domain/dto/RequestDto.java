package com.solutions.computic.Todo.domain.dto;

/**
 * All the requests should inherit this interface for generalizing requests.
 * All the request specific validations should be defined in this interface
 */
public interface RequestDto extends BaseDto {
    
    /**
     * This method is used to validate if all the requeried parameters to process the request exists
     * @return true/false
     */
    boolean isRequiredAvailable();
}
