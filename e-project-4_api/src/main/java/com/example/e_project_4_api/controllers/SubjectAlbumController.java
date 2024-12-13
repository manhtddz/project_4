package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateSubjectAlbum;
import com.example.e_project_4_api.dto.response.common_response.SubjectAlbumResponse;
import com.example.e_project_4_api.service.SubjectAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SubjectAlbumController {

    @Autowired
    private SubjectAlbumService subjectAlbumService;


    @GetMapping("/public/subject-album")
    public ResponseEntity<List<SubjectAlbumResponse>> getAllSubjectAlbums() {
        List<SubjectAlbumResponse> subjectAlbums = subjectAlbumService.getAllSubjectAlbums();
        return new ResponseEntity<>(subjectAlbums, HttpStatus.OK);
    }

    @GetMapping("/public/subject-album/{id}")
    public ResponseEntity<SubjectAlbumResponse> getSubjectAlbumById(@PathVariable int id) {
        SubjectAlbumResponse subjectAlbum = subjectAlbumService.findById(id);
        return new ResponseEntity<>(subjectAlbum, HttpStatus.OK);
    }

    @PostMapping("/public/subject-album")
    public ResponseEntity<NewOrUpdateSubjectAlbum> createSubjectAlbum(@RequestBody NewOrUpdateSubjectAlbum request) {
        NewOrUpdateSubjectAlbum createdSubjectAlbum = subjectAlbumService.addNewSubjectAlbum(request);
        return new ResponseEntity<>(createdSubjectAlbum, HttpStatus.CREATED);
    }

    @PutMapping("public/subject-album")
    public ResponseEntity<NewOrUpdateSubjectAlbum> updateSubjectAlbum(@RequestBody NewOrUpdateSubjectAlbum request) {
        NewOrUpdateSubjectAlbum updatedSubjectAlbum = subjectAlbumService.updateSubjectAlbum(request);
        return new ResponseEntity<>(updatedSubjectAlbum, HttpStatus.OK);
    }


    @DeleteMapping("public/subject-album/{id}")
    public ResponseEntity<Object> deleteSubjectAlbum(@PathVariable int id) {
        subjectAlbumService.deleteById(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "Deleted successfully"
                ),
                HttpStatus.OK
        );
    }
}
