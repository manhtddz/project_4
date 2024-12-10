package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateGenres;
import com.example.e_project_4_api.dto.response.common_response.GenresResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Genres;
import com.example.e_project_4_api.repositories.GenresRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenresService {
    @Autowired
    private GenresRepository repo;


    public List<GenresResponse> getAllGenres() {
        return repo.findAll()
                .stream()
                .map(this::toGenreResponse)
                .collect(Collectors.toList());
    }


    public GenresResponse findById(int id) {
        Optional<Genres> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any genre with id: " + id);
        }
        return toGenreResponse(op.get());
    }


    public boolean deleteById(int id) {
        Optional<Genres> genre = repo.findById(id);
        if (genre.isEmpty()) {
            throw new NotFoundException("Can't find any genre with id: " + id);
        }
        Genres existing = genre.get();
        existing.setIsDeleted(true);
        repo.save(existing);
        return true;
    }


    public NewOrUpdateGenres addNewGenre(NewOrUpdateGenres request) {
        List<String> errors = new ArrayList<>();


        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            errors.add("Title is required");
        } else {

            Optional<Genres> op = repo.findByTitle(request.getTitle());
            if (op.isPresent()) {
                errors.add("Already exist genre with title: " + request.getTitle());
            }
        }


        if (request.getImage() == null || request.getImage().isEmpty()) {
            errors.add("Image URL is required");
        }


        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }


        Genres newGenre = new Genres(
                request.getTitle(),
                request.getImage(),
                false,
                new Date(),
                new Date()
        );

        repo.save(newGenre);

        return request;
    }


    public NewOrUpdateGenres updateGenre(NewOrUpdateGenres request) {
        List<String> errors = new ArrayList<>();


        Optional<Genres> op = repo.findById(request.getId());
        if (op.isEmpty()) {
            errors.add("Can't find any genre with id: " + request.getId());
        }


        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            errors.add("Title is required");
        } else {

            Optional<Genres> opTitle = repo.findByTitle(request.getTitle());
            if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
                errors.add("Already exist genre with title: " + request.getTitle());
            }
        }


        if (request.getImage() == null || request.getImage().isEmpty()) {
            errors.add("Image URL is required");
        }


        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }


        Genres genre = op.get();
        genre.setTitle(request.getTitle());
        genre.setImage(request.getImage());
        genre.setModifiedAt(new Date());
        genre.setIsDeleted(request.getIsDeleted());
        repo.save(genre);

        return request;
    }


    public GenresResponse toGenreResponse(Genres genre) {
        GenresResponse res = new GenresResponse();
        BeanUtils.copyProperties(genre, res);
        return res;
    }
}
