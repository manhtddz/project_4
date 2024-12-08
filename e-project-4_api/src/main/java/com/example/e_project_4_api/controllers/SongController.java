package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.request.NewOrUpdatePlaylist;
import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.dto.response.PlaylistResponse;
import com.example.e_project_4_api.dto.response.SongResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.AlbumService;
import com.example.e_project_4_api.service.SongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SongController {
    @Autowired
    private SongService service;

    @GetMapping("/public/songs")
    public ResponseEntity<List<SongResponse>> findAll() {
        return new ResponseEntity<>(service.getAllSongs(), HttpStatus.OK);
    }

    @GetMapping("/public/songs/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        try {
            SongResponse song = service.findById(id);
            return new ResponseEntity<>(song, HttpStatus.OK);
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

    @DeleteMapping("/public/songs/{id}")
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

    @PostMapping("/public/songs")
    public ResponseEntity<Object> add(@RequestBody @Valid NewOrUpdateSong request) {
        try {
            NewOrUpdateSong newSong = service.addNewSong(request);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "Playlist added successfully",
                            "data", newSong
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

    @PutMapping("/public/songs")
    public ResponseEntity<Object> update(@RequestBody @Valid NewOrUpdateSong request) {
        try {
            NewOrUpdateSong updatedSong = service.updateSong(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Playlist updated successfully",
                            "data", updatedSong
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
