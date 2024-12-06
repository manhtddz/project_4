package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.LoginRequest;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {



    @Autowired
    private UserService service;


    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);

    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest user) {

        return service.verify(user);
    }


}
