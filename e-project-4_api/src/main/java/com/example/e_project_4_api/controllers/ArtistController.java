package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateArtist;
import com.example.e_project_4_api.dto.response.ArtistResponse;
import com.example.e_project_4_api.dto.response.GenresResponse;
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
        return new ResponseEntity<>(artistService.getAllArtists(), HttpStatus.OK);
    }


    @GetMapping("/public/artists/{id}")
    public ResponseEntity<Object> getArtistById(@PathVariable int id) {
        try {
            ArtistResponse artist = artistService.findById(id);
            return new ResponseEntity<>(artist, HttpStatus.OK);
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
    public ResponseEntity<Object> deleteArtist(@PathVariable int id) {
        try {
            artistService.deleteById(id);
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
}
