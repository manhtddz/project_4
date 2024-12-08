package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateGenres;
import com.example.e_project_4_api.dto.response.GenresResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Genres;
import com.example.e_project_4_api.repositories.GenreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenresService {
    @Autowired
    private GenreRepository repo;

    public List<GenresResponse> getAllGenres() {
        return repo.findAll()
                .stream()
                .map(this::toGenresResponse)
                .collect(Collectors.toList());
    }

    public GenresResponse findById(int id) {
        Optional<Genres> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any genre with id: " + id);
        }
        return toGenresResponse(op.get());
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

        Optional<Genres> op = repo.findById(request.getId());
        if (op.isPresent()) {
            errors.add("Already exist genre with id: " + request.getId());
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Genres newGenre = new Genres(request.getTitle(), request.getImage(), request.getIsDeleted(), request.getCreatedAt(), request.getModifiedAt());
        repo.save(newGenre);
        return request;
    }

    public NewOrUpdateGenres updateGenre(NewOrUpdateGenres request) {
        List<String> errors = new ArrayList<>();

        Optional<Genres> op = repo.findById(request.getId());
        if (op.isEmpty()) {
            errors.add("Can't find any genre with id: " + request.getId());
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Genres genre = op.get();
        genre.setTitle(request.getTitle());
        genre.setImage(request.getImage());
        genre.setIsDeleted(request.getIsDeleted());
        genre.setModifiedAt(request.getModifiedAt());
        repo.save(genre);

        return request;
    }

    public GenresResponse toGenresResponse(Genres genre) {
        GenresResponse res = new GenresResponse();
        BeanUtils.copyProperties(genre, res);
        return res;
    }
}
