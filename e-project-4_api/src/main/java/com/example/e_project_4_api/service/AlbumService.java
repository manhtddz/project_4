package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.response.AlbumResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Subjects;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.AlbumRepository;
import com.example.e_project_4_api.repositories.ArtistRepository;
import com.example.e_project_4_api.repositories.SubjectRepository;
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

public class AlbumService {
    @Autowired
    private AlbumRepository repo;
    @Autowired
    private ArtistRepository artistRepo;
    @Autowired
    private SubjectRepository subjectRepo;

    public List<AlbumResponse> getAllAlbums() {
        return repo.findAll()
                .stream()
                .map(this::toAlbumResponse)
                .collect(Collectors.toList());
    }

    public AlbumResponse findById(int id) {
        Optional<Albums> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + id);
        }
        return toAlbumResponse(op.get());
    }

    public boolean deleteById(int id) {
        Optional<Albums> album = repo.findById(id);
        if (album.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + id);
        }
            Albums existing = album.get();
            existing.setIsDeleted(true);
            repo.save(existing);
            return true;
    }

    public NewOrUpdateAlbum addNewAlbum(NewOrUpdateAlbum request) {
        List<String> errors = new ArrayList<>();

        Optional<Albums> op = repo.findById(request.getId());
        if (op.isPresent()) {
            errors.add("Already exist album with id: " + request.getId());
        }
        Optional<Artists> artist = artistRepo.findById(request.getArtistId());
        if (artist.isEmpty()) {
            errors.add("Can't find any artist with id: " + request.getArtistId());
        }
        Optional<Subjects> subject = subjectRepo.findById(request.getSubjectId());
        if (subject.isEmpty()) {
            errors.add("Can't find any subject with id: " + request.getSubjectId());
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Albums newAlbum = new Albums(request.getTitle(), request.getImage(), false, request.getReleaseDate(),
                false, request.getCreatedAt(), request.getModifiedAt(), artist.get(), subject.get());
        repo.save(newAlbum);
        return request;
    }

    public NewOrUpdateAlbum updateAlbum(NewOrUpdateAlbum request) {
        List<String> errors = new ArrayList<>();

        Optional<Albums> op = repo.findById(request.getId());
        if (op.isEmpty()) {
            errors.add("Can't find any album with id: " + request.getId());
        }
        Optional<Artists> artist = artistRepo.findById(request.getArtistId());
        if (artist.isEmpty()) {
            errors.add("Can't find any artist with id: " + request.getArtistId());
        }
        Optional<Subjects> subject = subjectRepo.findById(request.getSubjectId());
        if (subject.isEmpty()) {
            errors.add("Can't find any subject with id: " + request.getSubjectId());
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Albums album = op.get();
        album.setTitle(request.getTitle());
        album.setImage(request.getImage());
        album.setReleaseDate(request.getReleaseDate());
        album.setSubjectId(subject.get());
        album.setIsReleased(request.getReleased());
        album.setArtistId(artist.get());
        album.setIsDeleted(request.getDeleted());
        album.setModifiedAt(request.getModifiedAt());
        repo.save(album);

        return request;
    }

    public AlbumResponse toAlbumResponse(Albums album) {
        AlbumResponse res = new AlbumResponse();
        BeanUtils.copyProperties(album, res);
        res.setArtistId(album.getArtistId().getId());
        res.setSubjectId(album.getSubjectId().getId());
        return res;
    }
}
