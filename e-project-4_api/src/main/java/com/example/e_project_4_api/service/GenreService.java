package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.GenresRequest;
import com.example.e_project_4_api.dto.response.GenresResponse;
import com.example.e_project_4_api.models.Genres;
import com.example.e_project_4_api.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;


    private Genres convertToEntity(GenresRequest genresRequest) {
        Genres genre = new Genres();
        genre.setTitle(genresRequest.getTitle());
        genre.setImage(genresRequest.getImage());
        genre.setIsDeleted(genresRequest.getIsDeleted());
        genre.setCreatedAt(new Date());
        genre.setModifiedAt(new Date());
        return genre;
    }


    private GenresResponse convertToResponse(Genres genre) {
        return new GenresResponse(
                genre.getId(),
                genre.getTitle(),
                genre.getImage(),
                genre.getIsDeleted(),
                genre.getCreatedAt(),
                genre.getModifiedAt()
        );
    }


    public GenresResponse createGenre(GenresRequest genresRequest) {
        Genres genre = convertToEntity(genresRequest);
        Genres createdGenre = genreRepository.save(genre);
        return convertToResponse(createdGenre);
    }


    public List<GenresResponse> getAllGenres() {
        List<Genres> genres = genreRepository.findAll();
        return genres.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    public Optional<GenresResponse> getGenreById(Integer id) {
        Optional<Genres> genre = genreRepository.findById(id);
        return genre.map(this::convertToResponse);
    }


    public GenresResponse updateGenre(Integer id, GenresRequest genresRequest) {
        Optional<Genres> existingGenre = genreRepository.findById(id);
        if (existingGenre.isPresent()) {
            Genres genre = existingGenre.get();
            genre.setTitle(genresRequest.getTitle());
            genre.setImage(genresRequest.getImage());
            genre.setIsDeleted(genresRequest.getIsDeleted());
            genre.setModifiedAt(new Date());
            genreRepository.save(genre);
            return convertToResponse(genre);
        }
        return null;
    }


    public boolean deleteGenre(Integer id) {
        Optional<Genres> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            Genres existing = genre.get();
            existing.setIsDeleted(true);
            genreRepository.save(existing);
            return true;
        }
        return false;
    }
}
