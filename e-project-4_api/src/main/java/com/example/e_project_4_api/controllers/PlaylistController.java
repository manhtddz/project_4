package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdatePlaylist;
import com.example.e_project_4_api.dto.response.common_response.PlaylistResponse;
import com.example.e_project_4_api.dto.response.display_response.PlaylistDisplay;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlaylistController {
    @Autowired
    private PlaylistService service;

    @GetMapping("/public/playlists")
    public ResponseEntity<List<PlaylistResponse>> findAll() {
        return new ResponseEntity<>(service.getAllPlaylists(), HttpStatus.OK);
    }

    @GetMapping("/public/playlists/display")
    public ResponseEntity<List<PlaylistDisplay>> findAllPlaylistsForDisplay() {
        return new ResponseEntity<>(service.getAllPlaylistsForDisplay(), HttpStatus.OK);
    }

    @GetMapping("/public/playlists/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        PlaylistResponse playlist = service.findById(id);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }
    @GetMapping("/public/playlists/display/{id}")
    public ResponseEntity<Object> findDisplayDetails(@PathVariable("id") int id) {
        PlaylistDisplay playlist = service.findDisplayById(id);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @DeleteMapping("/public/playlists/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        service.deleteById(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "Deleted successfully"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/public/playlists")
    public ResponseEntity<Object> add(@RequestBody @Valid NewOrUpdatePlaylist request) {
        try {
            NewOrUpdatePlaylist newPlaylist = service.addNewPlaylist(request);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "Playlist added successfully",
                            "data", newPlaylist
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

    @PutMapping("/public/playlists")
    public ResponseEntity<Object> update(@RequestBody @Valid NewOrUpdatePlaylist request) {
        try {
            NewOrUpdatePlaylist updatedPlaylist = service.updatePlaylist(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Playlist updated successfully",
                            "data", updatedPlaylist
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
