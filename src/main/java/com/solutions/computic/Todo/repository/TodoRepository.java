package com.solutions.computic.Todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.solutions.computic.Todo.domain.entity.Todo;
import com.solutions.computic.Todo.domain.entity.User;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {

    Page<Todo> findByOwnedUser(User user, Pageable pageable);

    @Query("SELECT t FROM Todo t WHERE t.ownedUser = :user AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Todo> findByOwnedUserAndProperty(User user, String keyword, Pageable pageable);
}
