package com.solutions.computic.Todo.domain.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solutions.computic.Todo.exception.TodoAppBaseException;

public interface BaseDto {
    
    /**
     * This method converts this object into a json string
     * @return Json
     */
    default String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new TodoAppBaseException("Object to Json conversion is failed", e);
        }
    }

    /**
     * This method is used to validate if a given string parameter is not empty
     * @param parameter parameter
     * @return true/false
     */
    default boolean isNonEmpty(String parameter) {
        return parameter != null && !parameter.trim().isEmpty();
    }
}
