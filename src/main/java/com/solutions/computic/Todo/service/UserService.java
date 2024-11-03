package com.solutions.computic.Todo.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.solutions.computic.Todo.domain.entity.User;
import com.solutions.computic.Todo.exception.TodoAppBaseException;
import com.solutions.computic.Todo.exception.UserExistsException;
import com.solutions.computic.Todo.exception.UserNotFoundException;
import com.solutions.computic.Todo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public User createUser(String name, String email, String rawPwd) {
        try {
            if (isUserExists(email)) {
                throw new UserExistsException("Service level error, User already found in db for given email: " + email);
            }
            var user = new User(name, email, passwordEncoder.encode(rawPwd));
            return repository.save(user);
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when creating user", e);
        }
    }

    public User authenticate(String email, String rawPwd) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, rawPwd));
            return getByEmail(email);
        } catch (AuthenticationException e) {
            throw new TodoAppBaseException("Service level error when authenticating user credentials", e);
        }
    }

    public User getByEmail(String email) {
        try {
            if (!isUserExists(email)) {
                throw new UserNotFoundException("Service level error, User not found in db for given email: " + email);
            }
            return repository.findByEmail(email).get();
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when getting user by email", e);
        }
    }

    private boolean isUserExists(String email) {
        try {
            if (!repository.findByEmail(email).isPresent()) {
                return false;
            }
            return true;
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when checking user exists by email", e);
        }
    }

    public User getCurrentUser() {
        try {
            var authPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            var authUsername = ((org.springframework.security.core.userdetails.User) authPrincipal).getUsername();
            var optionalUser = repository.findByEmail(authUsername);
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException("Service level error, user not found when getting current user");
            }
            return optionalUser.get();
        } catch (DataAccessException e) {
            throw new TodoAppBaseException("Service level error when getting current user", e);
        }
    }
}
