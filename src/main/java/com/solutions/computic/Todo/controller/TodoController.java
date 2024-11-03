package com.solutions.computic.Todo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.computic.Todo.domain.dto.requests.CreateTodoRequestDto;
import com.solutions.computic.Todo.domain.dto.response.TodoListResponseDto;
import com.solutions.computic.Todo.domain.dto.response.TodoResponseDto;
import com.solutions.computic.Todo.domain.entity.Todo;
import com.solutions.computic.Todo.enums.ErrorResponseStatusType;
import com.solutions.computic.Todo.enums.SuccessResponseStatusType;
import com.solutions.computic.Todo.enums.TodoStatus;
import com.solutions.computic.Todo.service.TodoService;
import com.solutions.computic.Todo.utils.ResponseGenerator;
import com.solutions.computic.Todo.wrapper.ResponseWrapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final ResponseGenerator responseGenerator;
    private final TodoService service;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createTodo(@RequestBody CreateTodoRequestDto requestDto) {
        if (!requestDto.isRequiredAvailable()) {
            log.error("Todo creation failed, Cause => Requests required fields missing");
            return responseGenerator.generateErrorResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
        }
        var todo = service.createTodo(requestDto);
        var responseDto = new TodoResponseDto(todo);
        log.info("Todo creation successfull, Todo data => " + responseDto.toLogJson());
        return responseGenerator.generateSuccessResponse(responseDto, SuccessResponseStatusType.CREATE_TOOD);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> getTodos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, 
    @RequestParam(defaultValue = "title") String sortBy, @RequestParam(defaultValue = "true") boolean ascending, @RequestParam(defaultValue = "") 
    String searchTerm) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Todo> todoPage;
        if (searchTerm.isEmpty()) todoPage = service.findAll(pageable);
        else todoPage = service.findAllByProperty(searchTerm, pageable);
        var responseDto = new TodoListResponseDto(todoPage);
        log.info("Todo retreival successfull, Data => " + responseDto.toLogJson());
        return responseGenerator.generateSuccessResponse(responseDto, SuccessResponseStatusType.GET_TODO);
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateTodo(@RequestParam(defaultValue = "PENDING") String status, @PathVariable(name = "id") String id) {
        var todo = service.updateStatus(id, TodoStatus.valueOf(status));
        var responseDto = new TodoResponseDto(todo);
        log.info("Todo update successfull, Data => " + responseDto.toLogJson());
        return responseGenerator.generateSuccessResponse(responseDto, SuccessResponseStatusType.UPDATE_TODO);
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> deleteTodo(@PathVariable(name = "id") String id) {
        service.delete(id);
        log.info("Todo delete successfull for id: " + id);
        return responseGenerator.generateSuccessResponse(null, SuccessResponseStatusType.DELETE_TODO);
    }
}
