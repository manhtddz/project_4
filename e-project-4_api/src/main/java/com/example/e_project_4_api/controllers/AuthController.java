package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.LoginRequest;
import com.example.e_project_4_api.dto.request.NewOrUpdateUser;
import com.example.e_project_4_api.dto.response.common_response.LoginResponse;
import com.example.e_project_4_api.dto.response.common_response.UserResponse;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody NewOrUpdateUser user) {
        try {
            UserResponse newUser = service.register(user);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "Register successfully",
                            "data", newUser
                    ),
                    HttpStatus.OK
            );
        } catch (ValidationException e) {
            return new ResponseEntity<>(
                    Map.of(
                            "error", e.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest user) {
        try {
            LoginResponse res = service.verify(user);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "Login successfully",
                            "token", res
                    ),
                    HttpStatus.OK
            );
        } catch (ValidationException ex) {
            return new ResponseEntity<>(
                    Map.of(
                            "error", ex.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
