package com.solutions.computic.Todo.domain.dto.response;

import java.util.Date;

import com.solutions.computic.Todo.domain.dto.ResponseDto;
import com.solutions.computic.Todo.domain.entity.Todo;
import com.solutions.computic.Todo.enums.TodoPriority;
import com.solutions.computic.Todo.enums.TodoStatus;

import lombok.Getter;

@Getter
public class TodoResponseDto implements ResponseDto {
    
    private final String title;
    private final String description;
    private final TodoPriority priority;
    private final TodoStatus status;
    private final Date dueDate;

    public TodoResponseDto(Todo todo) {
        this.title = todo.getTitle();
        if (!isNonEmpty(todo.getDescription())) {
            this.description = todo.getTitle();
        } else {
            this.description = todo.getDescription();
        }
        this.priority = todo.getPriority();
        this.status = todo.getStatus();
        this.dueDate = todo.getDueDate();
    }

    @Override
    public String toLogJson() {
        return toJson();
    }
}
