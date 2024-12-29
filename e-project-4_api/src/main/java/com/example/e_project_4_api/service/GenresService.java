package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateGenres;
import com.example.e_project_4_api.dto.response.common_response.GenresResponse;
import com.example.e_project_4_api.dto.response.display_for_admin.GenreDisplayForAdmin;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Genres;
import com.example.e_project_4_api.repositories.GenreSongRepository;
import com.example.e_project_4_api.repositories.GenresRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenresService {
    @Autowired
    private GenresRepository repo;
    @Autowired
    private GenreSongRepository gSrepo;

    @Cacheable("genresDisplay")
    public List<GenresResponse> getAllGenres() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toGenreResponse)
                .collect(Collectors.toList());
    }
    public int getNumberOfGenre() {
        return repo.getNumberOfAllNotDeleted(false);
    }

    @Cacheable("genresDisplayForAdmin")
    public List<GenreDisplayForAdmin> getAllGenreDisplayForAdmin() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toGenreDisplayForAdmin)
                .collect(Collectors.toList());
    }


    public GenresResponse findById(int id) {
        Optional<Genres> op = repo.findByIdAndIsDeleted(id, false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any genre with id: " + id);
        }
        return toGenreResponse(op.get());
    }

    public GenreDisplayForAdmin findGenreDisplayForAdminById(int id) {
        Optional<Genres> op = repo.findByIdAndIsDeleted(id, false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any genre with id: " + id);
        }
        return toGenreDisplayForAdmin(op.get());
    }

    @CacheEvict(value = {"genresDisplay", "genresDisplayForAdmin", "songsDisplayForAdmin", "songsDisplay", "songsByArtist", "songsByAlbum", "favSongs", "songsByGenre", "songsByPlaylist"}, allEntries = true)
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

    @CacheEvict(value = {"genresDisplay", "genresDisplayForAdmin", "songsDisplayForAdmin", "songsDisplay", "songsByArtist", "songsByAlbum", "favSongs", "songsByGenre", "songsByPlaylist"}, allEntries = true)
    public NewOrUpdateGenres addNewGenre(NewOrUpdateGenres request) {
        List<Map<String, String>> errors = new ArrayList<>();


        Optional<Genres> op = repo.findByTitle(request.getTitle());
        if (op.isPresent()) {
            errors.add(Map.of("titleError", "Already exist title"));
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

    @CacheEvict(value = {"genresDisplay", "genresDisplayForAdmin", "songsDisplayForAdmin", "songsDisplay", "songsByArtist", "songsByAlbum", "favSongs", "songsByGenre", "songsByPlaylist"}, allEntries = true)
    public NewOrUpdateGenres updateGenre(NewOrUpdateGenres request) {
        List<Map<String, String>> errors = new ArrayList<>();


        Optional<Genres> op = repo.findByIdAndIsDeleted(request.getId(), false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any genre with id: " + request.getId());
        }


        Optional<Genres> opTitle = repo.findByTitle(request.getTitle());
        if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
            errors.add(Map.of("titleError", "Already exist title"));
        }


        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }


        Genres genre = op.get();
        genre.setTitle(request.getTitle());
        genre.setImage(request.getImage());
        genre.setModifiedAt(new Date());
        repo.save(genre);

        return request;
    }


    public GenresResponse toGenreResponse(Genres genre) {
        GenresResponse res = new GenresResponse();
        BeanUtils.copyProperties(genre, res);
        return res;
    }

    public GenreDisplayForAdmin toGenreDisplayForAdmin(Genres genre) {
        GenreDisplayForAdmin res = new GenreDisplayForAdmin();
        BeanUtils.copyProperties(genre, res);
        res.setTotalSong(gSrepo.findByGenreId(genre.getId(), false)
                .stream()
                .toList()
                .size());
        return res;
    }
}
