package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.request.NewOrUpdatePlaylist;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.dto.response.PlaylistResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Playlists;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.AlbumRepository;
import com.example.e_project_4_api.repositories.PlaylistRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class PlaylistService {
    @Autowired
    private PlaylistRepository repo;
    @Autowired
    private UserRepository userRepo;

    public List<PlaylistResponse> getAllPlaylists() {
        return repo.findAll()
                .stream()
                .map(this::toPlayListResponse)
                .collect(Collectors.toList());
    }

    public PlaylistResponse findById(int id) {
        Optional<Playlists> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any playlists with id: " + id);
        }
        return toPlayListResponse(op.get());
    }

    public boolean deleteById(int id) {
        Optional<Playlists> playlist = repo.findById(id);
        if (playlist.isEmpty()) {
            throw new NotFoundException("Can't find any playlist with id: " + id);
        }
        Playlists existing = playlist.get();
        existing.setIsDeleted(true);
        repo.save(existing);
        return true;
    }

    public NewOrUpdatePlaylist addNewPlaylist(NewOrUpdatePlaylist request) {
        List<String> errors = new ArrayList<>();
        if (request.getTitle().isEmpty() || (request.getTitle() == null)) {
            //check null
            errors.add("Title is required");
            // playlist ko cần check unique title
        }
        Optional<Users> user = userRepo.findById(request.getUserId());

        if (user.isEmpty()) {
            errors.add("Can't find any user with id: " + request.getUserId());
        }
        Playlists newPlaylist = new Playlists(request.getTitle(), false, request.getCreatedAt(),
                request.getModifiedAt(), user.get());
        repo.save(newPlaylist);
        return request;
    }

    public NewOrUpdatePlaylist updatePlaylist(NewOrUpdatePlaylist request) {
        List<String> errors = new ArrayList<>();

        Optional<Playlists> op = repo.findById(request.getId());
        if (op.isEmpty()) {
            errors.add("Can't find any playlist with id: " + request.getId());
        }
        if (request.getTitle().isEmpty() || (request.getTitle() == null)) {
            //check null
            errors.add("Title is required");
            // playlist ko cần check unique title
        }
        Optional<Users> user = userRepo.findById(request.getUserId());
        if (user.isEmpty()) {
            errors.add("Can't find any user with id: " + request.getUserId());
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Playlists playlist = op.get();
        playlist.setTitle(request.getTitle());
        playlist.setIsDeleted(request.getDeleted());
        playlist.setUserId(user.get());
        playlist.setIsDeleted(request.getDeleted());
        playlist.setModifiedAt(request.getModifiedAt());
        repo.save(playlist);
        return request;
    }

    public PlaylistResponse toPlayListResponse(Playlists playlist) {
        PlaylistResponse res = new PlaylistResponse();
        BeanUtils.copyProperties(playlist, res);
        res.setDeleted(playlist.getIsDeleted());
        res.setUserId(playlist.getUserId().getId());
        return res;
    }
}
