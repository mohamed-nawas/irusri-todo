package com.solutions.computic.Todo.domain.dto.requests;

import com.solutions.computic.Todo.domain.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserRequestDto implements RequestDto {
    
    private final String name;
    private final String email;
    private final String pwd;

    @Override
    public boolean isRequiredAvailable() {
        return isNonEmpty(email) && isNonEmpty(pwd);
    }
}
