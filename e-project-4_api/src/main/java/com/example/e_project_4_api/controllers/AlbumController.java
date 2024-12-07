package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AlbumController {
    @Autowired
    private AlbumService service;

    @GetMapping("/public/albums")
    public ResponseEntity<List<AlbumResponse>> findAll() {
        return new ResponseEntity<>(service.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/public/albums/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        try {
            AlbumResponse album = service.findById(id);
            return new ResponseEntity<>(album, HttpStatus.OK);
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

    @DeleteMapping("/public/albums/{id}")
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

    @PostMapping("/public/albums")
    public ResponseEntity<Object> add(@RequestBody @Valid NewOrUpdateAlbum request) {
        try {
            NewOrUpdateAlbum newAlbum = service.addNewAlbum(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Album added successfully",
                            "data", newAlbum
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

    @PutMapping("/public/albums")
    public ResponseEntity<Object> update(@RequestBody @Valid NewOrUpdateAlbum request) {
        try {
            NewOrUpdateAlbum updatedAlbum = service.updateAlbum(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Album updated successfully",
                            "data", updatedAlbum
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
