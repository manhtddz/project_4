package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateGenres;
import com.example.e_project_4_api.dto.response.GenresResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GenresController {
    @Autowired
    private GenresService service;

    @GetMapping("/public/genres")
    public ResponseEntity<List<GenresResponse>> findAll() {
        return new ResponseEntity<>(service.getAllGenres(), HttpStatus.OK);
    }

    @GetMapping("/public/genres/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        try {
            GenresResponse genre = service.findById(id);
            return new ResponseEntity<>(genre, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(
                    Map.of(
                            "error", "Not found",
                            "details", ex.getMessage()
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/public/genres/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "Deleted successfully"
                    ),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            return new ResponseEntity<>(
                    Map.of(
                            "error", "Not found",
                            "details", e.getMessage()
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping("/public/genres")
    public ResponseEntity<Object> add(@RequestBody NewOrUpdateGenres request) {
        try {
            NewOrUpdateGenres newGenre = service.addNewGenre(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Genre added successfully",
                            "data", newGenre
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

    @PutMapping("/public/genres")
    public ResponseEntity<Object> update(@RequestBody NewOrUpdateGenres request) {
        try {
            NewOrUpdateGenres updatedGenre = service.updateGenre(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Genre updated successfully",
                            "data", updatedGenre
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
}
