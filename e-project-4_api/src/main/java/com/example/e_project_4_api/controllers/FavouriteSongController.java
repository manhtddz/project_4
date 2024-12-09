package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateFavouriteSong;
import com.example.e_project_4_api.dto.response.FavouriteSongResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.FavouriteSongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
class FavouriteSongController {
    @Autowired
    public FavouriteSongService service;

    @GetMapping("/public/favourite-songs")
    public ResponseEntity<List<FavouriteSongResponse>> findAll() {
        return new ResponseEntity<>(service.getAllFavouriteSong(), HttpStatus.OK);
    }

    @GetMapping("/public/favourite-songs/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        try {
            FavouriteSongResponse sub = service.findById(id);
            return new ResponseEntity<>(sub, HttpStatus.OK);
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

    @DeleteMapping("/public/favourite-songs/{id}")
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

    @PostMapping("/public/favourite-songs")
    public ResponseEntity<Object> add(@RequestBody @Valid NewOrUpdateFavouriteSong request) {
        try {
            NewOrUpdateFavouriteSong newSub = service.addNewPS(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "FavouriteSong added successfully",
                            "data", newSub
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

    @PutMapping("/public/favourite-songs")
    public ResponseEntity<Object> update(@RequestBody @Valid NewOrUpdateFavouriteSong request) {
        try {
            NewOrUpdateFavouriteSong updatedSub = service.updatePS(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "FavouriteSong updated successfully",
                            "data", updatedSub
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