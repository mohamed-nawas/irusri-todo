package com.solutions.computic.Todo.domain.dto.response;

import com.solutions.computic.Todo.domain.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponseDto implements ResponseDto {
    
    private final String token;
    private final int expirtationHour;

    @Override
    public String toLogJson() {
        return toJson();
    }
}
