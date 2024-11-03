package com.solutions.computic.Todo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.solutions.computic.Todo.domain.dto.ResponseDto;
import com.solutions.computic.Todo.domain.dto.requests.CreateTodoRequestDto;
import com.solutions.computic.Todo.domain.dto.response.TodoResponseDto;
import com.solutions.computic.Todo.domain.entity.Todo;
import com.solutions.computic.Todo.domain.entity.User;
import com.solutions.computic.Todo.enums.ErrorResponseStatusType;
import com.solutions.computic.Todo.enums.SuccessResponseStatusType;
import com.solutions.computic.Todo.enums.TodoPriority;
import com.solutions.computic.Todo.enums.TodoStatus;
import com.solutions.computic.Todo.exception.GlobalExceptionHandler;
import com.solutions.computic.Todo.service.TodoService;
import com.solutions.computic.Todo.utils.ResponseGenerator;
import com.solutions.computic.Todo.wrapper.ResponseWrapper;
import com.solutions.computic.Todo.wrapper.SuccessResponseWrapper;

public class TodoControllerTest {

    @Mock
    private TodoService service;
    @Mock
    private ResponseGenerator responseGenerator;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        openMocks(this);
        var controller = new TodoController(responseGenerator, service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler(responseGenerator)).build();
    }

    @AfterEach
    void tear() throws Exception {
        openMocks(this).close();
    }

    /**
     * Test for createTodo
     * Route => /api/v1/todo/create
     * 
     * @throws Exception
     */
    @Test
    void Should_Return201_When_CreatingTodoSuccessfull() throws Exception {
        var todo = new Todo("some title", "some description", TodoPriority.HIGH, new Date(), new User());
        var requestDto = new CreateTodoRequestDto("some title");
        requestDto.setPriority(TodoPriority.HIGH);
        requestDto.setDueDate(new Date());
        when(service.createTodo(any(CreateTodoRequestDto.class))).thenReturn(todo);
        when(responseGenerator.generateSuccessResponse(any(ResponseDto.class), any(SuccessResponseStatusType.class)))
        .thenReturn(new ResponseEntity<ResponseWrapper>(HttpStatus.CREATED));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/todo/create")
                .header("Authorization", "Bearer eyj")
                .content(requestDto.toJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void Should_Return400_When_CreatingTodo_WithoutRequiredParams() throws Exception {
        var todo = new Todo("some title", "some description", TodoPriority.HIGH, new Date(), new User());
        var requestDto = new CreateTodoRequestDto("some title");
        requestDto.setPriority(TodoPriority.HIGH);
        when(service.createTodo(any(CreateTodoRequestDto.class))).thenReturn(todo);
        when(responseGenerator.generateErrorResponse(any(ErrorResponseStatusType.class)))
        .thenReturn(new ResponseEntity<ResponseWrapper>(HttpStatus.BAD_REQUEST));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/todo/create")
                .header("Authorization", "Bearer eyj")
                .content(requestDto.toJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test for updateTodo
     * Route => /api/v1/todo/update/{id}
     * 
     * @throws Exception
     */
    @Test
    void Should_Return200_When_UpdatingTodoSuccessfull_And_UpdateData() throws Exception {
        var todo = new Todo("some title", "some description", TodoPriority.HIGH, new Date(), new User());
        when(service.updateStatus(anyString(), any(TodoStatus.class))).thenReturn(todo);
        when(responseGenerator.generateSuccessResponse(any(ResponseDto.class), any(SuccessResponseStatusType.class)))
        .thenReturn(new ResponseEntity<ResponseWrapper>(new SuccessResponseWrapper(new TodoResponseDto(todo), "message", 200), HttpStatus.OK));


        var uri = "/api/v1/todo/update/{id}".replace("{id}", "TID-123");
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .param("status", "PENDING")
                .header("Authorization", "Bearer eyj")
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("some title"));
    }
}
