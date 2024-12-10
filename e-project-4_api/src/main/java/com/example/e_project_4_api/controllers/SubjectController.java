package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateSubject;
import com.example.e_project_4_api.dto.response.common_response.SubjectResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
class SubjectController {
    @Autowired
    public SubjectService service;

    @GetMapping("/public/subjects")
    public ResponseEntity<List<SubjectResponse>> findAll() {
        return new ResponseEntity<>(service.getAllSubjects(), HttpStatus.OK);
    }

    @GetMapping("/public/subjects/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        SubjectResponse sub = service.findById(id);
        return new ResponseEntity<>(sub, HttpStatus.OK);
    }

    @DeleteMapping("/public/subjects/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        service.deleteById(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "Deleted successfully"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/public/subjects")
    public ResponseEntity<Object> add(@RequestBody @Valid NewOrUpdateSubject request) {
        try {
            NewOrUpdateSubject newSub = service.addNewSubject(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Subject added successfully",
                            "data", newSub
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

    @PutMapping("/public/subjects")
    public ResponseEntity<Object> update(@RequestBody @Valid NewOrUpdateSubject request) {
        try {
            NewOrUpdateSubject updatedSub = service.updateSubject(request);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Subject updated successfully",
                            "data", updatedSub
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