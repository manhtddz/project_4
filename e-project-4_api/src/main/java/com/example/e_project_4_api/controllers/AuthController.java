package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.LoginRequest;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.service.UserService;
import com.example.e_project_4_api.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;


    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.register(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String token = userService.verify(loginRequest);
        if (token != null) {
            return token;
        }
        return "fail";
    }
}
