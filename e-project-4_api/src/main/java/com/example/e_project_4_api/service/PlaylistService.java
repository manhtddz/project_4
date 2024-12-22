package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdatePlaylist;
import com.example.e_project_4_api.dto.response.common_response.PlaylistResponse;
import com.example.e_project_4_api.dto.response.display_response.PlaylistDisplay;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Playlists;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.PlaylistRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
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
                .map(this::toPlaylistResponse)
                .collect(Collectors.toList());
    }

    @Cacheable("playlistsDisplay")
    public List<PlaylistDisplay> getAllPlaylistsForDisplay() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toPlaylistDisplay)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "playlistsByUser", key = "#userId")
    public List<PlaylistDisplay> getAllPlaylistsByUserIdForDisplay(int userId) {
        return repo.findAllByUserId(userId, false)
                .stream()
                .map(this::toPlaylistDisplay)
                .collect(Collectors.toList());
    }

    public PlaylistResponse findById(int id) {
        Optional<Playlists> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any playlists with id: " + id);
        }
        return toPlaylistResponse(op.get());
    }

    public PlaylistDisplay findDisplayById(int id) {
        Optional<Playlists> op = repo.findByIdAndIsDeleted(id, false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any playlists with id: " + id);
        }
        return toPlaylistDisplay(op.get());
    }

    @CacheEvict(value = {"playlistsByUser", "playlistsDisplay"}, allEntries = true)
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

    @CacheEvict(value = {"playlistsByUser", "playlistsDisplay"}, allEntries = true)
    public NewOrUpdatePlaylist addNewPlaylist(NewOrUpdatePlaylist request) {
        List<Map<String, String>> errors = new ArrayList<>();
        Optional<Users> user = userRepo.findByIdAndIsDeleted(request.getUserId(), false);

        if (user.isEmpty()) {
            errors.add(Map.of("userError", "Can't find user"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Playlists newPlaylist = new Playlists(request.getTitle(), false, new Date(),
                new Date(), user.get());
        repo.save(newPlaylist);
        return request;
    }

    @CacheEvict(value = {"playlistsByUser", "playlistsDisplay"}, allEntries = true)
    public NewOrUpdatePlaylist updatePlaylist(NewOrUpdatePlaylist request) {
        List<Map<String, String>> errors = new ArrayList<>();
        Optional<Playlists> op = repo.findByIdAndIsDeleted(request.getId(), false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any playlist with id: " + request.getId());
        }

        Optional<Users> user = userRepo.findByIdAndIsDeleted(request.getUserId(), false);
        if (user.isEmpty()) {
            errors.add(Map.of("userError", "Can't find user"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Playlists playlist = op.get();
        playlist.setTitle(request.getTitle());
        playlist.setUserId(user.get());
        playlist.setModifiedAt(new Date());
        repo.save(playlist);
        return request;
    }

    public PlaylistResponse toPlaylistResponse(Playlists playlist) {
        PlaylistResponse res = new PlaylistResponse();
        BeanUtils.copyProperties(playlist, res);
        res.setIsDeleted(playlist.getIsDeleted());
        res.setUserId(playlist.getUserId().getId());
        return res;
    }

    public PlaylistDisplay toPlaylistDisplay(Playlists playlist) {
        PlaylistDisplay res = new PlaylistDisplay();
        BeanUtils.copyProperties(playlist, res);
        res.setIsDeleted(playlist.getIsDeleted());
        res.setUsername(playlist.getUserId().getUsername());
        return res;
    }
}
