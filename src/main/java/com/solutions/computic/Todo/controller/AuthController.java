package com.solutions.computic.Todo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.computic.Todo.domain.dto.requests.CreateUserRequestDto;
import com.solutions.computic.Todo.domain.dto.requests.LoginRequestDto;
import com.solutions.computic.Todo.domain.dto.response.TokenResponseDto;
import com.solutions.computic.Todo.domain.dto.response.UserResponseDto;
import com.solutions.computic.Todo.enums.ErrorResponseStatusType;
import com.solutions.computic.Todo.enums.SuccessResponseStatusType;
import com.solutions.computic.Todo.service.JwtService;
import com.solutions.computic.Todo.service.UserService;
import com.solutions.computic.Todo.utils.ResponseGenerator;
import com.solutions.computic.Todo.wrapper.ResponseWrapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService service;
    private final JwtService jwtService;
    private final ResponseGenerator responseGenerator;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> register(@RequestBody CreateUserRequestDto requestDto) {
        if (!requestDto.isRequiredAvailable()) {
            log.error("User registration failed, Cause => Requests required fields missing");
            return responseGenerator.generateErrorResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
        }
        var user = service.createUser(requestDto.getName(), requestDto.getEmail(), requestDto.getPwd());
        var responseDto = new UserResponseDto(user.getName(), user.getEmail());
        log.info("User registration successfull, User data => " + responseDto.toLogJson());
        return responseGenerator.generateSuccessResponse(responseDto, SuccessResponseStatusType.REGISTER);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> login(@RequestBody LoginRequestDto requestDto) {
        if (!requestDto.isRequiredAvailable()) {
            log.error("User login failed, Cause => Requests required fields missing");
            return responseGenerator.generateErrorResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
        }
        var user = service.authenticate(requestDto.getEmail(), requestDto.getPwd());
        var token = jwtService.generateToken(user);
        var responseDto = new TokenResponseDto(token, jwtService.getExpiration());
        log.info("User login successfull, Token => " + responseDto.toLogJson());
        return responseGenerator.generateSuccessResponse(responseDto, SuccessResponseStatusType.LOGIN);
    }
}
