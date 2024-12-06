package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.request.NewOrUpdatePlaylist;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.dto.response.PlaylistResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Playlists;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.AlbumRepository;
import com.example.e_project_4_api.repositories.PlaylistRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public void deleteById(int id) {
        Optional<Playlists> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any playlists with id: " + id);
        }
        repo.delete(op.get());
    }

    public NewOrUpdatePlaylist addNewPlaylist(NewOrUpdatePlaylist request) {
        Optional<Playlists> op = repo.findById(request.getId());
        Optional<Users> users = userRepo.findById(request.getUserId());
        if (op.isPresent()) {
            throw new AlreadyExistedException("Found a album with Id: " + request.getId());
        }
        if (users.isEmpty()) {
            throw new NotFoundException("Can't find any users with Id: " + request.getUserId());
        }
        Playlists newPlaylist = new Playlists(request.getId(), request.getTitle(), request.getDeleted(), request.getCreatedAt(),
                request.getModifiedAt(), users.get());
        repo.save(newPlaylist);
        return request;
    }

    public NewOrUpdatePlaylist updatePlaylist(NewOrUpdatePlaylist request) {
        Optional<Playlists> op = repo.findById(request.getId());
        Optional<Users> users = userRepo.findById(request.getUserId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any student with id: " + request.getId());
        }
        if (users.isEmpty()) {
            throw new NotFoundException("Can't find any users with id: " + request.getUserId());
        }
        Playlists playlist = op.get();
        playlist.setTitle(request.getTitle());
        playlist.setIsDeleted(request.getDeleted());
        playlist.setModifiedAt(request.getModifiedAt());
        playlist.setUserId(users.get());
        playlist.setIsDeleted(request.getDeleted());
        playlist.setModifiedAt(request.getModifiedAt());
        repo.save(playlist);
        return request;
    }

    public PlaylistResponse toPlayListResponse(Playlists playlist) {
        PlaylistResponse res = new PlaylistResponse();
        BeanUtils.copyProperties(playlist, res);
        res.setUserId(playlist.getUserId().getId());
        return res;
    }
}
