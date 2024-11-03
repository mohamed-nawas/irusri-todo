package com.solutions.computic.Todo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;

import com.solutions.computic.Todo.domain.dto.requests.CreateTodoRequestDto;
import com.solutions.computic.Todo.domain.entity.Todo;
import com.solutions.computic.Todo.domain.entity.User;
import com.solutions.computic.Todo.exception.TodoAppBaseException;
import com.solutions.computic.Todo.repository.TodoRepository;

public class TodoServiceTest {
    
    @Mock
    private UserService userService;
    @Mock
    private TodoRepository repository;
    
    private TodoService service;

    @BeforeEach
    void setup() {
        openMocks(this);
        service = new TodoService(userService, repository);
    }

    @AfterEach
    void tear() throws Exception {
        openMocks(this).close();
    }

    /**
     * createTodo method test
     */
    @Test
    void Should_CreateTodo_When_ValidArgsPassed() {
        var user = new User("nawas", "mgmnawas@gmail.com", "123");
        when(userService.getCurrentUser()).thenReturn(user);
        var requestDto = new CreateTodoRequestDto("Some title");

        service.createTodo(requestDto);

        ArgumentCaptor<Todo> todoCaptor = forClass(Todo.class);
        verify(repository).save(todoCaptor.capture());

        var savedTodo = todoCaptor.getValue();

        assertEquals("Some title", savedTodo.getTitle());
        assertEquals(user, savedTodo.getOwnedUser());
    }

    @Test
    void Should_ThrowTodoAppBaseException_When_DbAccessErrorOccurred() {
        var requestDto = new CreateTodoRequestDto("Some title");
        when(repository.save(any(Todo.class))).thenThrow(new DataAccessException("Error") {
        });

        var exception = assertThrows(TodoAppBaseException.class, () -> service.createTodo(requestDto));
        
        assertEquals("Service level error when creating todo", exception.getMessage());
    }

}
