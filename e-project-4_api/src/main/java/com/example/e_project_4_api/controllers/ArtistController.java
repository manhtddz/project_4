package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateArtist;
import com.example.e_project_4_api.dto.response.ArtistResponse;
import com.example.e_project_4_api.service.ArtistService;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ArtistController {

    @Autowired
    private ArtistService artistService;


    @GetMapping("/public/artists")
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        List<ArtistResponse> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }


    @GetMapping("/public/artists/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable int id) {
        try {
            ArtistResponse artist = artistService.findById(id);
            return ResponseEntity.ok(artist);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/public/artists")
    public ResponseEntity<Object> addNewArtist(@RequestBody NewOrUpdateArtist request) {
        try {
            NewOrUpdateArtist newArtist = artistService.addNewArtist(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newArtist);
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


    @PutMapping("/public/artists")
    public ResponseEntity<Object> updateArtist(@RequestBody NewOrUpdateArtist request) {
        try {
            NewOrUpdateArtist updatedArtist = artistService.updateArtist(request);
            return ResponseEntity.ok(updatedArtist);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(
                    Map.of(
                            "error", "Validation failed",
                            "details", ex.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("/public/artists/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable int id) {
        try {
            boolean isDeleted = artistService.deleteById(id);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Artist deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artist not found");
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artist not found");
        }
    }
}
