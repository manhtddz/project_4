package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateGenres;
import com.example.e_project_4_api.dto.request.NewOrUpdateNews;
import com.example.e_project_4_api.dto.response.common_response.GenresResponse;
import com.example.e_project_4_api.dto.response.common_response.NewsResponse;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.GenresService;
import com.example.e_project_4_api.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private NewsService service;


    @GetMapping("/public/news")
    public ResponseEntity<List<NewsResponse>> findAll() {
        return new ResponseEntity<>(service.getAllNews(), HttpStatus.OK);
    }


    @GetMapping("/public/news/{id}")
    public ResponseEntity<Object> findDetails(@PathVariable("id") int id) {
        NewsResponse news = service.findById(id);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }


    @DeleteMapping("/public/news/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        service.deleteById(id);
        return new ResponseEntity<>(
                Map.of(
                        "message", "Deleted successfully"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/public/news")
    public ResponseEntity<Object> add(@RequestBody @Valid NewOrUpdateNews request) {
        try {
            NewOrUpdateNews news = service.addNew(request);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "News added successfully",
                            "data", news
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


    @PutMapping("/public/news")
    public ResponseEntity<Object> update(@RequestBody @Valid NewOrUpdateNews request) {
        try {
            NewOrUpdateNews updatedNews = service.updateNews(request);
            return new ResponseEntity<>(
                    Map.of(
                            "message", "News updated successfully",
                            "data", updatedNews
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
