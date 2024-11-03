package com.solutions.computic.Todo.domain.dto.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.solutions.computic.Todo.domain.entity.Todo;

import lombok.Getter;

@Getter
public class TodoListResponseDto extends PageResponseDto {

    private List<TodoResponseDto> todos;

    public TodoListResponseDto(Page<Todo> todoPage) {
        super(todoPage);
        this.todos = new ArrayList<>();
        for (Todo todo : todoPage) {
            var todoResponseDto = new TodoResponseDto(todo);
            todos.add(todoResponseDto);
        }
    }
}
