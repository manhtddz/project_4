package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateUser;
import com.example.e_project_4_api.dto.request.UpdateUserWithAttribute;
import com.example.e_project_4_api.dto.response.common_response.UserResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/public/users")
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/public/users/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        UserResponse album = service.findById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }


    @DeleteMapping("/public/users/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        service.deleteById(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "Deleted successfully"
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/public/users")
    public ResponseEntity<Object> update(@RequestBody @Valid NewOrUpdateUser request) {
        try {
            UserResponse updatedUser = service.updateUser(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "User updated successfully",
                            "data", updatedUser
                    ),
                    HttpStatus.OK
            );
        } catch (ValidationException e) {
            return new ResponseEntity<>(
                    Map.of(
                            "listError", e.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/public/users/updatePart")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateUserWithAttribute request) {
        try {
            service.updateEachPartOfUser(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "User updated successfully"
                    ),
                    HttpStatus.OK
            );
        } catch (ValidationException e) {
            return new ResponseEntity<>(
                    Map.of(
                            "listError", e.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
