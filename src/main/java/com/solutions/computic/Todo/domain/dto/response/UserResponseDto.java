package com.solutions.computic.Todo.domain.dto.response;

import com.solutions.computic.Todo.domain.dto.ResponseDto;

import lombok.Getter;

@Getter
public class UserResponseDto implements ResponseDto {
    
    private final String name;

    public UserResponseDto(String name, String email) {
        if (!isNonEmpty(name)) {
            this.name = email;
        } else {
            this.name = name;
        }
    }

    @Override
    public String toLogJson() {
        return toJson();
    }
}
