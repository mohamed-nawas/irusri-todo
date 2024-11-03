package com.solutions.computic.Todo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.solutions.computic.Todo.domain.dto.ResponseDto;
import com.solutions.computic.Todo.enums.ErrorResponseStatusType;
import com.solutions.computic.Todo.enums.SuccessResponseStatusType;
import com.solutions.computic.Todo.wrapper.ErrorResponseWrapper;
import com.solutions.computic.Todo.wrapper.ResponseWrapper;
import com.solutions.computic.Todo.wrapper.SuccessResponseWrapper;

@Component
public class ResponseGenerator {
    
    /**
     * This method is used to format a generic API response for success scenarios
     * @param data Response data
     * @param status Response type
     * @return API response
     */
    public ResponseEntity<ResponseWrapper> generateSuccessResponse(ResponseDto data, SuccessResponseStatusType status) {
        var responseWrapper = new SuccessResponseWrapper(data, status.getMessage(), status.getCode());
        return new ResponseEntity<>(responseWrapper, HttpStatus.valueOf(status.getCode()));
    }

    /**
     * This method is used to format a generic API response for error scenarios
     * @param status Response type
     * @return API response
     */
    public ResponseEntity<ResponseWrapper> generateErrorResponse(ErrorResponseStatusType status) {
        var responseWrapper = new ErrorResponseWrapper(status.getMessage(), status.getCode());
        return new ResponseEntity<>(responseWrapper, HttpStatus.valueOf(status.getCode()));
    }
}
