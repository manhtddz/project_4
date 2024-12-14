package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateGenreSong;
import com.example.e_project_4_api.dto.response.common_response.GenreSongResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.GenreSong;
import com.example.e_project_4_api.models.Genres;
import com.example.e_project_4_api.models.Songs;
import com.example.e_project_4_api.repositories.GenreSongRepository;
import com.example.e_project_4_api.repositories.GenresRepository;
import com.example.e_project_4_api.repositories.SongRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenreSongService {

    @Autowired
    private GenreSongRepository genreSongRepo;
    @Autowired
    private GenresRepository genreRepo;
    @Autowired
    private SongRepository songRepo;

    // Lấy tất cả GenreSong
    public List<GenreSongResponse> getAllGenreSongs() {
        return genreSongRepo.findAll()
                .stream()
                .map(this::toGenreSongResponse)
                .collect(Collectors.toList());
    }


    public GenreSongResponse findById(int id) {
        Optional<GenreSong> op = genreSongRepo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any genre-song relation with id: " + id);
        }
        return toGenreSongResponse(op.get());
    }


    public boolean deleteById(int id) {
        Optional<GenreSong> genreSong = genreSongRepo.findById(id);
        if (genreSong.isEmpty()) {
            throw new NotFoundException("Can't find any genre-song relation with id: " + id);
        }
        GenreSong existing = genreSong.get();
        genreSongRepo.delete(existing);
        return true;
    }


    public NewOrUpdateGenreSong addNewGenreSong(NewOrUpdateGenreSong request) {
        List<String> errors = new ArrayList<>();

        Optional<GenreSong> existingGenreSong = genreSongRepo.findByGenreIdAndSongId(request.getGenreId(), request.getSongId());
        if (existingGenreSong.isPresent()) {
            errors.add("A GenreSong already exists");
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


        GenreSong newGenreSong = new GenreSong(
                new Date(),
                new Date(),
                genre.get(),
                song.get()
        );

        genreSongRepo.save(newGenreSong);
        return request;
    }


    public NewOrUpdateGenreSong updateGenreSong(NewOrUpdateGenreSong request) {
        List<String> errors = new ArrayList<>();

        Optional<GenreSong> existingGenreSong = genreSongRepo.findById(request.getId());
        if (existingGenreSong.isEmpty()) {
            errors.add("Can't find any genre-song relation with id: " + request.getId());
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


        GenreSong genreSong = existingGenreSong.get();
        genreSong.setModifiedAt(new Date());
        genreSong.setGenreId(genre.get());
        genreSong.setSongId(song.get());
        genreSongRepo.save(genreSong);

        return request;
    }

    public GenreSongResponse toGenreSongResponse(GenreSong genreSong) {
        GenreSongResponse res = new GenreSongResponse();
        BeanUtils.copyProperties(genreSong, res);
        res.setGenreId(genreSong.getGenreId().getId());
        res.setSongId(genreSong.getSongId().getId());
        return res;
    }
}
