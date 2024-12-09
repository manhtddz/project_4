package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdatePlaylistSong;
import com.example.e_project_4_api.dto.response.PlaylistSongResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.PlaylistSongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
class PlaylistSongController {
    @Autowired
    public PlaylistSongService service;

    @GetMapping("/public/playlist-song")
    public ResponseEntity<List<PlaylistSongResponse>> findAll() {
        return new ResponseEntity<>(service.getAllPlaylistSong(), HttpStatus.OK);
    }

    @GetMapping("/public/playlist-song/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        try {
            PlaylistSongResponse sub = service.findById(id);
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

    @DeleteMapping("/public/playlist-song/{id}")
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

    @PostMapping("/public/playlist-song")
    public ResponseEntity<Object> add(@RequestBody @Valid NewOrUpdatePlaylistSong request) {
        try {
            NewOrUpdatePlaylistSong newSub = service.addNewPS(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "PlaylistSong added successfully",
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

    @PutMapping("/public/playlist-song")
    public ResponseEntity<Object> update(@RequestBody @Valid NewOrUpdatePlaylistSong request) {
        try {
            NewOrUpdatePlaylistSong updatedSub = service.updatePS(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "PlaylistSong updated successfully",
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