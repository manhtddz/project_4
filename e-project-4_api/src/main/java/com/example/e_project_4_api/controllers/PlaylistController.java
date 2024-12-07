package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.request.NewOrUpdatePlaylist;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.dto.response.PlaylistResponse;
import com.example.e_project_4_api.service.AlbumService;
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
    public List<PlaylistResponse> findAll() {
        return service.getAllPlaylists();
    }

    @GetMapping("/public/playlists/{id}")
    public PlaylistResponse findDetails(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @DeleteMapping("/public/playlists/{id}")
    public  ResponseEntity<Void> delete(@PathVariable("id") int id) {

        if(service.deleteById(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/public/playlists")
    public Map<String, Object> add(@RequestBody @Valid NewOrUpdatePlaylist request) {
        NewOrUpdatePlaylist res = service.addNewPlaylist(request);
        return Map.of(
                "message", "Add success",
                "data", res
        );
    }

    @PutMapping("/public/playlists")
    public Map<String, Object> update(@RequestBody @Valid NewOrUpdatePlaylist request) {
        NewOrUpdatePlaylist res = service.updatePlaylist(request);
        return Map.of(
                "message", "Update success",
                "data", res
        );
    }
}
