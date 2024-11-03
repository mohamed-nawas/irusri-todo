package com.solutions.computic.Todo.domain.entity;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solutions.computic.Todo.enums.TodoPriority;
import com.solutions.computic.Todo.enums.TodoStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Todo {
    
    @Transient
    private static final String TODO_ID_PREFIX = "TID-";

    @Id
    private String id;
    @Column(nullable = false)
    private String title;
    @Column
    private String description;
    @Column(nullable = false)
    private TodoPriority priority;
    @Column(nullable = false)
    private TodoStatus status;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User ownedUser;

    public Todo(String title, String description, TodoPriority priority, Date dueDate, User user) {
        this.id = TODO_ID_PREFIX + UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = TodoStatus.PENDING;
        this.dueDate = dueDate;
        this.ownedUser = user;
    }

    public void updateStatus(TodoStatus status) { this.status = status; }
}
