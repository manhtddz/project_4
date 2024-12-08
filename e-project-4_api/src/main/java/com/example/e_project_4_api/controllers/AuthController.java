package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.LoginRequest;
import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
import com.example.e_project_4_api.dto.request.RegisterRequest;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.service.AuthenticationService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Object> register(@RequestBody RegisterRequest user) {
        try {
            Users newUser = service.register(user);
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
                            "error", "Validation failed",
                            "details", e.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest user) {
        try {
            String token = service.verify(user);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "Login successfully",
                            "token", token
                    ),
                    HttpStatus.OK
            );
        } catch (ValidationException ex) {
            return new ResponseEntity<>(
                    Map.of(
                            "error", "Validation failed",
                            "details", ex.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(
                    Map.of(
                            "error", "Validation failed",
                            "details", ex.getMessage()
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }


}
