package com.solutions.computic.Todo.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.solutions.computic.Todo.domain.dto.requests.CreateTodoRequestDto;
import com.solutions.computic.Todo.domain.entity.Todo;
import com.solutions.computic.Todo.enums.TodoStatus;
import com.solutions.computic.Todo.exception.PermissionDeniedException;
import com.solutions.computic.Todo.exception.TodoAppBaseException;
import com.solutions.computic.Todo.exception.TodoNotFoundException;
import com.solutions.computic.Todo.repository.TodoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoService {

    private final UserService userService;
    private final TodoRepository repository;

    public Todo createTodo(CreateTodoRequestDto dto) {
        try {
            var user = userService.getCurrentUser();
            var todo = new Todo(dto.getTitle(), dto.getDescription(), dto.getPriority(), dto.getDueDate(), user);
            return repository.save(todo);
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when creating todo", e);
        }
    }

    public Page<Todo> findAll(Pageable pageable) {
        try {
            var user = userService.getCurrentUser();
            return repository.findByOwnedUser(user, pageable);
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when getting all todos", e);
        }
    }

    public Page<Todo> findAllByProperty(String keyword, Pageable pageable) {
        try {
            var user = userService.getCurrentUser();
            return repository.findByOwnedUserAndProperty(user, keyword, pageable);
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when getting all todos by keyword", e);
        }
    }

    public Todo updateStatus(String id, TodoStatus status) {
        try {
            Todo todo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo item for given id not found"));
            if (todo.getOwnedUser().getEmail() != userService.getCurrentUser().getEmail()) {
                throw new PermissionDeniedException("Service level error, updating todo status not allowed as you are not the owner");
            }
            todo.updateStatus(status);
            return repository.save(todo);
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when updating todo status", e);
        }
    }

    public void delete(String id) {
        try {
            Todo todo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo item for given id not found"));
            if (todo.getOwnedUser().getEmail() != userService.getCurrentUser().getEmail()) {
                throw new PermissionDeniedException("Service level error, deleting todo not allowed as you are not the owner");
            }
            repository.delete(todo);
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when deleting todo", e);
        }
    }
}
