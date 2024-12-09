package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateFavouriteSong;
import com.example.e_project_4_api.dto.response.FavouriteSongResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.models.FavouriteSongs;
import com.example.e_project_4_api.models.Playlists;
import com.example.e_project_4_api.models.Songs;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.PlaylistRepository;
import com.example.e_project_4_api.repositories.FavouriteSongRepository;
import com.example.e_project_4_api.repositories.SongRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteSongService {
    @Autowired
    FavouriteSongRepository repo;

    @Autowired
    UserRepository userRepo;
    @Autowired
    SongRepository songRepo;

    public List<FavouriteSongResponse> getAllFavouriteSong() {
        return repo.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public FavouriteSongResponse findById(int id) {
        Optional<FavouriteSongs> op = repo.findById(id);
        if (op.isPresent()) {
            FavouriteSongs fs = op.get();
            return toResponse(fs);
        } else {
            throw new NotFoundException("Can't find any FavouriteSong with id: " + id);
        }
    }

    public void deleteById(int id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Can't find any FavouriteSong with id: " + id);
        }
        repo.deleteById(id);
    }

    public NewOrUpdateFavouriteSong addNewPS(NewOrUpdateFavouriteSong request) {
        Optional<FavouriteSongs> existingFavouriteSong = repo.findById(request.getId());
        if (existingFavouriteSong.isPresent()) {
            throw new AlreadyExistedException("A FavouriteSong already exists");
        }
        Users u = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + request.getUserId()));
        Songs song = songRepo.findById(request.getSongId())
                .orElseThrow(() -> new NotFoundException("Song not found with id: " + request.getSongId()));
        Date currentDate = new Date();
        FavouriteSongs newPS = new FavouriteSongs(false,currentDate,currentDate,song,u);
        repo.save(newPS);
        return request;
    }

    public NewOrUpdateFavouriteSong updatePS(NewOrUpdateFavouriteSong request) {
        Optional<FavouriteSongs> op = repo.findById(request.getId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any FavouriteSong with id: " + request.getId());
        }
        Users u = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("Users not found with id: " + request.getUserId()));
        Songs song = songRepo.findById(request.getSongId())
                .orElseThrow(() -> new NotFoundException("Song not found with id: " + request.getSongId()));
        Date currentDate = new Date();
        FavouriteSongs ps = op.get();
        ps.setUserId(u);
        ps.setSongId(song);
        ps.setIsDeleted(false);
        ps.setCreatedAt(currentDate);
        ps.setModifiedAt(currentDate);

        repo.save(ps);
        return request;
    }

    private FavouriteSongResponse toResponse(FavouriteSongs ps) {
        FavouriteSongResponse res = new FavouriteSongResponse();
        res.setUserId(ps.getUserId().getId());
        res.setSongId(ps.getSongId().getId());
        res.setIsDeleted(ps.getIsDeleted());
        BeanUtils.copyProperties(ps, res);
        return res;
    }
}