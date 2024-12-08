package com.example.e_project_4_api.controllers;



import com.example.e_project_4_api.dto.request.ArtistRequest;
import com.example.e_project_4_api.dto.response.ArtistResponse;
import com.example.e_project_4_api.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ArtistController {

    @Autowired
    private ArtistService artistService;


    @PostMapping("/public/artist")
    public ResponseEntity<ArtistResponse> createArtist(@RequestBody ArtistRequest artistRequest) {
        ArtistResponse response = artistService.createOrUpdateArtist(artistRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/public/artist/{id}")
    public ResponseEntity<ArtistResponse> updateArtist(@PathVariable Integer id, @RequestBody ArtistRequest artistRequest) {
        artistRequest.setCreatedAt(null);
        artistRequest.setModifiedAt(new Date());

        ArtistResponse response = artistService.updateArtist(id, artistRequest);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/public/artist/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable Integer id) {
        ArtistResponse response = artistService.getArtistById(id);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/public/artist")
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        List<ArtistResponse> responses = artistService.getAllArtists();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    @DeleteMapping("/public/artist/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Integer id) {
        boolean isDeleted = artistService.deleteArtist(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
    }
}
