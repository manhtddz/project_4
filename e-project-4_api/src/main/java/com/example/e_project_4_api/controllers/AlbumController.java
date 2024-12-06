package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AlbumController {
    @Autowired
    private AlbumService service;

    @GetMapping("/public/albums")
    public List<AlbumResponse> findAll() {
        return service.getAllAlbums();
    }

    @GetMapping("/public/albums/{id}")
    public AlbumResponse findDetails(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @DeleteMapping("/public/albums/{id}")
    public Map<String, String> delete(@PathVariable("id")int id) {
        service.deleteById(id);
        return Map.of(
                "message", "Delete album " + id
        );
    }

    @PostMapping("/public/albums")
    public Map<String, Object> add(@RequestBody @Valid NewOrUpdateAlbum request) {
        NewOrUpdateAlbum res = service.addNewAlbum(request);
        return Map.of(
                "message", "Add success",
                "data", res
        );
    }

    @PutMapping("/public/albums")
    public Map<String, Object> update(@RequestBody @Valid NewOrUpdateAlbum request) {
        NewOrUpdateAlbum res = service.updateAlbum(request);
        return Map.of(
                "message", "Update success",
                "data", res
        );
    }
}
