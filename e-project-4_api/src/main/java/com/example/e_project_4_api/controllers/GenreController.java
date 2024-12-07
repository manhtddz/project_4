package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.GenresRequest;
import com.example.e_project_4_api.dto.response.GenresResponse;
import com.example.e_project_4_api.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GenreController {

    @Autowired
    private GenreService genreService;


    @PostMapping("api/genre/create")
    public ResponseEntity<GenresResponse> createGenre(@RequestBody GenresRequest genresRequest) {
        GenresResponse createdGenre = genreService.createGenre(genresRequest);
        return new ResponseEntity<>(createdGenre, HttpStatus.CREATED);
    }


    @GetMapping("public/genre")
    public ResponseEntity<List<GenresResponse>> getAllGenres() {
        List<GenresResponse> genres = genreService.getAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }


    @GetMapping("public/genre/{id}")
    public ResponseEntity<GenresResponse> getGenreById(@PathVariable Integer id) {
        Optional<GenresResponse> genre = genreService.getGenreById(id);
        return genre.map(g -> new ResponseEntity<>(g, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("public/genre/{id}")
    public ResponseEntity<GenresResponse> updateGenre(@PathVariable Integer id, @RequestBody GenresRequest genresRequest) {
        GenresResponse updatedGenre = genreService.updateGenre(id, genresRequest);
        if (updatedGenre != null) {
            return new ResponseEntity<>(updatedGenre, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Integer id) {
        boolean isDeleted = genreService.deleteGenre(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
