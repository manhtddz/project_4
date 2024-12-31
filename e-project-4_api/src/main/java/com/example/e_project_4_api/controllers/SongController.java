package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateFavouriteSong;
import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
import com.example.e_project_4_api.dto.response.common_response.SongResponse;
import com.example.e_project_4_api.dto.response.display_for_admin.SongDisplayForAdmin;
import com.example.e_project_4_api.dto.response.display_response.SongDisplay;
import com.example.e_project_4_api.dto.response.mix_response.SongWithLikeAndViewInMonth;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
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

    @GetMapping("/public/songs/display")
    public ResponseEntity<List<SongDisplay>> findAllSongsForDisplay() {
        return new ResponseEntity<>(service.getAllSongsForDisplay(), HttpStatus.OK);
    }

    @GetMapping("/admin/songs/display")
    public ResponseEntity<List<SongDisplayForAdmin>> findAllSongsDisplayForAdmin() {
        return new ResponseEntity<>(service.getAllSongsForAdmin(), HttpStatus.OK);
    }

    @GetMapping("/admin/songs/count")
    public ResponseEntity<Object> getQuantity() {
        return new ResponseEntity<>(Map.of("qty", service.getNumberOfSong()), HttpStatus.OK);
    }

    @GetMapping("/public/songs/byArtist/display/{id}")
    public ResponseEntity<List<SongDisplay>> findAllSongsByArtistIdForDisplay(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.getAllSongsByArtistIdForDisplay(id), HttpStatus.OK);
    }

    @GetMapping("/public/songs/byAlbum/display/{id}")
    public ResponseEntity<List<SongDisplay>> findAllSongsByAlbumIdForDisplay(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.getAllSongsByAlbumIdForDisplay(id), HttpStatus.OK);
    }

    @GetMapping("/public/songs/byUser/display/{id}")
    public ResponseEntity<List<SongDisplay>> findAllSongsByUserIdForDisplay(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.getAllFavSongsByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/public/songs/byGenre/display/{id}")
    public ResponseEntity<List<SongDisplay>> findAllSongsByGenreIdForDisplay(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.getAllSongsByGenreId(id), HttpStatus.OK);
    }

    @GetMapping("/public/songs/byPlaylist/display/{id}")
    public ResponseEntity<List<SongDisplay>> findAllSongsByPlaylistIdForDisplay(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.getAllSongsByPlaylistId(id), HttpStatus.OK);
    }

    @GetMapping("/public/songs/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        SongResponse song = service.findById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);

    }

    @GetMapping("/public/songs/display/{id}")
    public ResponseEntity<Object> findDisplayDetails(@PathVariable("id") int id) {
        SongDisplay song = service.findDisplayById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/admin/songs/display/{id}")
    public ResponseEntity<Object> findDisplayDetailsForAdmin(@PathVariable("id") int id) {
        SongDisplayForAdmin song = service.findDisplayForAdminById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/public/songs/mostListened")
    public ResponseEntity<Object> findMostListenedSongInMonth() {
        SongWithLikeAndViewInMonth song = service.getMostListenedSongInMonth();
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/public/songs/mostFav")
    public ResponseEntity<Object> findMostFavouriteSongInMonth() {
        SongWithLikeAndViewInMonth song = service.getMostFavouriteSongInMonth();
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/public/songs/topFive")
    public ResponseEntity<Object> findTop5SongInMonth() {
        List<SongWithLikeAndViewInMonth> songs = service.getMost5ListenedSongInMonth();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }


    @DeleteMapping("/public/songs/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        service.deleteById(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "Deleted successfully"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/public/songs")
    public ResponseEntity<Object> add(@RequestBody @Valid NewOrUpdateSong request) {
        try {
            NewOrUpdateSong newSong = service.addNewSong(request);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "Song added successfully",
                            "data", newSong
                    ),
                    HttpStatus.OK
            );
        } catch (ValidationException e) {
            return new ResponseEntity<>(
                    Map.of(
                            "listError", e.getErrors()
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
                            "message", "Song updated successfully",
                            "data", updatedSong
                    ),
                    HttpStatus.OK
            );
        } catch (ValidationException e) {
            return new ResponseEntity<>(
                    Map.of(
                            "listError", e.getErrors()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/public/songs/listen/{id}")
    public ResponseEntity<Object> listen(@PathVariable("id") int id) {
        service.listen(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "listen successfully"
                ),
                HttpStatus.OK
        );

    }

    @PutMapping("/public/songs/like")
    public ResponseEntity<Object> likeSong(@RequestBody @Valid NewOrUpdateFavouriteSong request) {
        service.like(request);
        return new ResponseEntity<>(
                Map.of(
                        "message", "like successfully"
                ),
                HttpStatus.OK
        );

    }

    @PutMapping("/admin/songs/toggle/pending")
    public ResponseEntity<Object> toggleSongPending(@PathVariable("id") int id) {
        service.toggleSongPendingStatus(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "changes successfully"
                ),
                HttpStatus.OK
        );
    }
}
