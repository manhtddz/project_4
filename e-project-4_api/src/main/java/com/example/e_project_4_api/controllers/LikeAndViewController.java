package com.example.e_project_4_api.controllers;

import com.example.e_project_4_api.dto.request.NewOrUpdateGenres;
import com.example.e_project_4_api.dto.request.NewOrUpdateLikeAndViewInMonth;
import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
import com.example.e_project_4_api.dto.response.common_response.GenresResponse;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.service.GenresService;
import com.example.e_project_4_api.service.LikeAndViewInMonthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class LikeAndViewController {

    @Autowired
    private LikeAndViewInMonthService service;


    @GetMapping("/public/likeInMonth/{monthId}")
    public ResponseEntity<Object> totalLikeAmountInMonth(@PathVariable("monthId") int id) {
        return new ResponseEntity<>(Map.of(
                "total_like", service.totalLikeAmountInMonth(id)), HttpStatus.OK);
    }

    @GetMapping("/public/listenInMonth/{monthId}")
    public ResponseEntity<Object> totalListenAmountInMonth(@PathVariable("monthId") int id) {
        return new ResponseEntity<>(Map.of(
                "total_listen", service.totalListenAmountInMonth(id)), HttpStatus.OK);
    }

    @PostMapping("/public/likeInMonth")
    public ResponseEntity<Object> increaseLikeAmountOrCreateNew(@RequestBody @Valid NewOrUpdateLikeAndViewInMonth request) {
        service.increaseLikeAmountOrCreateNew(request);
        return new ResponseEntity<>(Map.of(
                "message", "add or increase successfully"), HttpStatus.OK);
    }

    @PostMapping("/public/listenInMonth")
    public ResponseEntity<Object> increaseListenAmountOrCreateNew(@RequestBody @Valid NewOrUpdateLikeAndViewInMonth request) {
        service.increaseListenAmountOrCreateNew(request);
        return new ResponseEntity<>(Map.of(
                "message", "add or increase successfully"), HttpStatus.OK);
    }

}
