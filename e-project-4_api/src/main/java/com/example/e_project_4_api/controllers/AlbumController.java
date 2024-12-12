package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.response.common_response.AlbumResponse;
import com.example.e_project_4_api.dto.response.display_response.AlbumDisplay;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AlbumController {
    @Autowired
    private AlbumService service;

    @GetMapping("/public/albums")
    public ResponseEntity<List<AlbumResponse>> findAll() {
        return new ResponseEntity<>(service.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/public/albums/display")
    public ResponseEntity<List<AlbumDisplay>> findAllAlbumsForDisplay() {
        return new ResponseEntity<>(service.getAllAlbumsForDisplay(), HttpStatus.OK);
    }

    @GetMapping("/public/albums/byArtist/display/{id}")
    public ResponseEntity<List<AlbumDisplay>> findAllAlbumsByArtistIdForDisplay(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.getAllAlbumsByArtistIdForDisplay(id), HttpStatus.OK);
    }

    @GetMapping("/public/albums/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        AlbumResponse album = service.findById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);

    }

    @GetMapping("/public/albums/display/{id}")
    public ResponseEntity<Object> findDisplayDetails(@PathVariable("id") int id) {
        AlbumDisplay album = service.findDisplayById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @DeleteMapping("/public/albums/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        service.deleteById(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "Deleted successfully"
                ),
                HttpStatus.OK
        );
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
                            "error", e.getErrors()
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
                            "error", e.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
