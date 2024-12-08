package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.GenreSongRequest;
import com.example.e_project_4_api.dto.response.GenreSongResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.GenreSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GenreSongController {

    @Autowired
    private GenreSongService service;

    @GetMapping("/public/genre-songs")
    public ResponseEntity<List<GenreSongResponse>> findAll() {
        return new ResponseEntity<>(service.getAllGenreSongs(), HttpStatus.OK);
    }

    @GetMapping("/public/genre-songs/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        try {
            GenreSongResponse genreSong = service.findById(id);
            return new ResponseEntity<>(genreSong, HttpStatus.OK);
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

    @DeleteMapping("/public/genre-songs/{id}")
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

    @PostMapping("/public/genre-songs")
    public ResponseEntity<Object> add(@RequestBody GenreSongRequest request) {
        try {
            GenreSongRequest newGenreSong = service.addNewGenreSong(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "GenreSong added successfully",
                            "data", newGenreSong
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

    @PutMapping("/public/genre-songs")
    public ResponseEntity<Object> update(@RequestBody GenreSongRequest request) {
        try {
            GenreSongRequest updatedGenreSong = service.updateGenreSong(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "GenreSong updated successfully",
                            "data", updatedGenreSong
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
