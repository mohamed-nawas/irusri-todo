package com.solutions.computic.Todo.config;

import java.util.Collections;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.solutions.computic.Todo.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = repository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            log.error("Spring security user details service error, Username not found");
            throw new UsernameNotFoundException("User credentials wrong");
        }
        var user = optionalUser.get();
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
