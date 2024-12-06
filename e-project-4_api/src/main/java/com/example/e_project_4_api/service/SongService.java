package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.dto.response.SongResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Songs;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.AlbumRepository;
import com.example.e_project_4_api.repositories.SongRepository;
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

public class SongService {
    @Autowired
    private SongRepository repo;
    @Autowired
    private AlbumRepository albumRepo;

    public List<SongResponse> getAllAlbums() {
        return repo.findAll()
                .stream()
                .map(this::toSongResponse)
                .collect(Collectors.toList());
    }

    public SongResponse findById(int id) {
        Optional<Songs> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any songs with id: " + id);
        }
        return toSongResponse(op.get());
    }

    public void deleteById(int id) {
        Optional<Songs> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any songs with id: " + id);
        }
        repo.delete(op.get());
    }

    public NewOrUpdateSong addNewSong(NewOrUpdateSong request) {
        Optional<Songs> op = repo.findById(request.getId());
        Optional<Albums> album = albumRepo.findById(request.getAlbumId());
        if (op.isPresent()) {
            throw new AlreadyExistedException("Found a song with Id: " + request.getId());
        }
        if (album.isEmpty()) {
            throw new NotFoundException("Can't find any albums with Id: " + request.getAlbumId());
        }
        Songs newSong = new Songs(request.getId(), request.getTitle(), request.getAudioPath(), request.getAmount(),
                request.getLikeAmount(), request.getLyricFilePath(), request.getPending(), request.getDeleted()
                , request.getCreatedAt(), request.getModifiedAt(), album.get());
        repo.save(newSong);
        return request;
    }

    public NewOrUpdateSong updateSong(NewOrUpdateSong request) {
        Optional<Songs> op = repo.findById(request.getId());
        Optional<Albums> album = albumRepo.findById(request.getAlbumId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any songs with id: " + request.getId());
        }
        if (album.isEmpty()) {
            throw new NotFoundException("Can't find any albums with id: " + request.getAlbumId());
        }
        Songs song = op.get();
        song.setTitle(request.getTitle());
        song.setAudioPath(request.getAudioPath());
        song.setAmount(request.getAmount());
        song.setLikeAmount(request.getLikeAmount());
        song.setLyricFilePath(request.getLyricFilePath());
        song.setIsPending(request.getPending());
        song.setIsDeleted(request.getDeleted());
        song.setModifiedAt(request.getModifiedAt());
        repo.save(song);
        return request;
    }

    public SongResponse toSongResponse(Songs song) {
        SongResponse res = new SongResponse();
        BeanUtils.copyProperties(song, res);
        res.setAlbumId(song.getAlbumId().getId());
        return res;
    }
}
