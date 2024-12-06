package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.AlbumRepository;
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

public class AlbumService {
    @Autowired
    private AlbumRepository repo;
    @Autowired
    private UserRepository userRepo;

    public List<AlbumResponse> getAllAlbums() {
        return repo.findAll()
                .stream()
                .map(this::toAlbumResponse)
                .collect(Collectors.toList());
    }

    public AlbumResponse findById(int id) {
        Optional<Albums> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any albums with id: " + id);
        }
        return toAlbumResponse(op.get());
    }

    public void deleteById(int id) {
        Optional<Albums> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any albums with id: " + id);
        }
        repo.delete(op.get());
    }

    public NewOrUpdateAlbum addNewAlbum(NewOrUpdateAlbum request) {
        Optional<Albums> op = repo.findById(request.getId());
        Optional<Users> artist = userRepo.findById(request.getArtistId());
        if (op.isPresent()) {
            throw new AlreadyExistedException("Found a album with Id: " + request.getId());
        }
        if (artist.isEmpty()) {
            throw new NotFoundException("Can't find any artists with Id: " + request.getArtistId());
        }
        Albums newAlbum = new Albums(request.getId(), request.getTitle(), request.getImage(), request.getReleaseDate(),
                artist.get(), request.getDeleted(), request.getCreatedAt(), request.getModifiedAt());
        repo.save(newAlbum);
        return request;
    }

    public NewOrUpdateAlbum updateAlbum(NewOrUpdateAlbum request) {
        Optional<Albums> op = repo.findById(request.getId());
        Optional<Users> artist = userRepo.findById(request.getArtistId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any student with id: " + request.getId());
        }
        if (artist.isEmpty()) {
            throw new NotFoundException("Can't find any artists with id: " + request.getArtistId());
        }
        Albums album = op.get();
        album.setTitle(request.getTitle());
        album.setImage(request.getImage());
        album.setReleaseDate(request.getReleaseDate());
        album.setArtistId(artist.get());
        album.setIsDeleted(request.getDeleted());
        album.setModifiedAt(request.getModifiedAt());
        repo.save(album);
        return request;
    }

    public AlbumResponse toAlbumResponse(Albums album){
        AlbumResponse res = new AlbumResponse();
        BeanUtils.copyProperties(album, res);
        res.setArtistId(album.getArtistId().getId());
        return res;
    }
}
