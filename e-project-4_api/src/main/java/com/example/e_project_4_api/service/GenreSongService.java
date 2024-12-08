package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.GenreSongRequest;
import com.example.e_project_4_api.dto.response.GenreSongResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.GenreSong;
import com.example.e_project_4_api.models.Genres;
import com.example.e_project_4_api.models.Songs;
import com.example.e_project_4_api.repositories.GenreSongRepository;
import com.example.e_project_4_api.repositories.GenreRepository;
import com.example.e_project_4_api.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreSongService {

    @Autowired
    private GenreSongRepository repo;
    @Autowired
    private GenreRepository genreRepo;
    @Autowired
    private SongRepository songRepo;

    public List<GenreSongResponse> getAllGenreSongs() {
        return repo.findAll()
                .stream()
                .map(this::toGenreSongResponse)
                .collect(Collectors.toList());
    }

    public GenreSongResponse findById(int id) {
        Optional<GenreSong> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any GenreSong with id: " + id);
        }
        return toGenreSongResponse(op.get());
    }

    public boolean deleteById(int id) {
        Optional<GenreSong> genreSong = repo.findById(id);
        if (genreSong.isEmpty()) {
            throw new NotFoundException("Can't find any GenreSong with id: " + id);
        }
        GenreSong existing = genreSong.get();
        existing.setIsDeleted(true);
        repo.save(existing);
        return true;
    }

    public GenreSongRequest addNewGenreSong(GenreSongRequest request) {
        List<String> errors = new ArrayList<>();

        Optional<Genres> genre = genreRepo.findById(request.getGenreId());
        if (genre.isEmpty()) {
            errors.add("Can't find any genre with id: " + request.getGenreId());
        }

        Optional<Songs> song = songRepo.findById(request.getSongId());
        if (song.isEmpty()) {
            errors.add("Can't find any song with id: " + request.getSongId());
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        GenreSong newGenreSong = new GenreSong(request.getIsDeleted(), request.getCreatedAt(), request.getModifiedAt(),
                genre.get(), song.get());
        repo.save(newGenreSong);
        return request;
    }

    public GenreSongRequest updateGenreSong(GenreSongRequest request) {
        List<String> errors = new ArrayList<>();

        Optional<GenreSong> op = repo.findById(request.getId());
        if (op.isEmpty()) {
            errors.add("Can't find any GenreSong with id: " + request.getId());
        }

        Optional<Genres> genre = genreRepo.findById(request.getGenreId());
        if (genre.isEmpty()) {
            errors.add("Can't find any genre with id: " + request.getGenreId());
        }

        Optional<Songs> song = songRepo.findById(request.getSongId());
        if (song.isEmpty()) {
            errors.add("Can't find any song with id: " + request.getSongId());
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        GenreSong genreSong = op.get();
        genreSong.setIsDeleted(request.getIsDeleted());
        genreSong.setCreatedAt(request.getCreatedAt());
        genreSong.setModifiedAt(request.getModifiedAt());
        genreSong.setGenreId(genre.get());
        genreSong.setSongId(song.get());
        repo.save(genreSong);

        return request;
    }

    private GenreSongResponse toGenreSongResponse(GenreSong genreSong) {
        GenreSongResponse res = new GenreSongResponse();
        BeanUtils.copyProperties(genreSong, res);
        res.setGenreId(genreSong.getGenreId().getId());
        res.setSongId(genreSong.getSongId().getId());
        return res;
    }
}
