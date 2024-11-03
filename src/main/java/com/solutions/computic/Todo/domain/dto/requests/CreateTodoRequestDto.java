package com.solutions.computic.Todo.domain.dto.requests;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solutions.computic.Todo.domain.dto.RequestDto;
import com.solutions.computic.Todo.enums.TodoPriority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateTodoRequestDto implements RequestDto {
    
    private final String title;
    private String description;
    private TodoPriority priority;        // format in string "HIGH", "MEDIUM", "LOW"
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;               // format a string "yyyy-MM-dd"

    @Override
    public boolean isRequiredAvailable() {
        return isNonEmpty(title) && priority != null && dueDate != null;
    }

    public CreateTodoRequestDto(String title) { this.title = title; }
}
