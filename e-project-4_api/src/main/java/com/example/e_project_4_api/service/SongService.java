package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.dto.response.SongResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.models.*;
import com.example.e_project_4_api.repositories.AlbumRepository;
import com.example.e_project_4_api.repositories.ArtistRepository;
import com.example.e_project_4_api.repositories.SongRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
    @Autowired
    private ArtistRepository artistRepo;

    public List<SongResponse> getAllSongs() {
        return repo.findAll()
                .stream()
                .map(this::toSongResponse)
                .collect(Collectors.toList());
    }

    public SongResponse findById(int id) {
        Optional<Songs> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any song with id: " + id);
        }
        return toSongResponse(op.get());
    }


    public boolean deleteById(int id) {
        Optional<Songs> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any song with id: " + id);
        }
        Songs existing = op.get();
        existing.setIsDeleted(true);
        repo.save(existing);
        return true;
    }

    public NewOrUpdateSong addNewSong(NewOrUpdateSong request) {
        List<String> errors = new ArrayList<>();
        if (request.getTitle().isEmpty() || (request.getTitle() == null)) {
            //check null
            errors.add("Title is required");
        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Songs> op = repo.findByTitle(request.getTitle());
            if (op.isPresent()) {
                errors.add("Already exist song with title: " + request.getTitle());
            }
        }
        //check null
        if (request.getAudioPath().isEmpty() || (request.getAudioPath() == null)) {
            errors.add("Audio path is required");
        }
        //check null
        if (request.getLyricFilePath().isEmpty() || (request.getLyricFilePath() == null)) {
            errors.add("Lyric file path is required");
        }
        Optional<Albums> album = albumRepo.findById(request.getAlbumId());
        Optional<Artists> artist = artistRepo.findById(request.getArtistId());
        if (artist.isEmpty()) {
            errors.add("Can't find any artist with id: " + request.getArtistId());
        }
        if (album.isEmpty()) {
            errors.add("Can't find any album with id: " + request.getAlbumId());
        }
        Songs newSong = new Songs(request.getTitle(), request.getAudioPath(), 0,
                0, request.getFeatureArtist(), request.getLyricFilePath(), false, false,
                request.getCreatedAt(), request.getModifiedAt(), album.get(), artist.get());
        repo.save(newSong);
        return request;
    }

    public NewOrUpdateSong updateSong(NewOrUpdateSong request) {
        List<String> errors = new ArrayList<>();

        Optional<Songs> op = repo.findById(request.getId());
        //check sự tồn tại
        if (op.isEmpty()) {
            errors.add("Can't find any song with id: " + request.getId());
        }
        if (request.getTitle().isEmpty() || (request.getTitle() == null)) {
            //check null
            errors.add("Title is required");
        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Songs> opTitle = repo.findByTitle(request.getTitle());
            if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
                errors.add("Already exist song with title: " + request.getTitle());
            }
        }
        Optional<Albums> album = albumRepo.findById(request.getAlbumId());
        Optional<Artists> artist = artistRepo.findById(request.getArtistId());
        if (artist.isEmpty()) {
            errors.add("Can't find any artist with id: " + request.getArtistId());
        }
        if (album.isEmpty()) {
            errors.add("Can't find any album with id: " + request.getAlbumId());
        }
        Songs song = op.get();
        song.setTitle(request.getTitle());
        song.setAudioPath(request.getAudioPath());
        song.setLikeAmount(request.getLikeAmount());
        song.setListenAmount(request.getListenAmount());
        song.setFeatureArtist(request.getFeatureArtist());
        song.setLyricFilePath(request.getLyricFilePath());
        song.setIsPending(request.getPending());
        song.setIsDeleted(request.getDeleted());
        song.setModifiedAt(request.getModifiedAt());
        song.setAlbumId(album.get());
        song.setArtistId(artist.get());
        repo.save(song);
        return request;
    }

    public SongResponse toSongResponse(Songs song) {
        SongResponse res = new SongResponse();
        BeanUtils.copyProperties(song, res);
        res.setDeleted(song.getIsDeleted());
        res.setPending(song.getIsPending());
        res.setAlbumId(song.getAlbumId().getId());
        res.setArtistId(song.getArtistId().getId());
        return res;
    }
}
