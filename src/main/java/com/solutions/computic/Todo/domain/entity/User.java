package com.solutions.computic.Todo.domain.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User implements UserDetails {
    
    @Transient
    private static final String USER_ID_PREFIX = "UID-";

    @Id
    private String id;
    @Column
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String pwd;
    @OneToMany(mappedBy = "ownedUser", fetch = FetchType.LAZY)
    private Set<Todo> todos;

    public User(String name, String email, String pwd) {
        this.id = USER_ID_PREFIX + UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
