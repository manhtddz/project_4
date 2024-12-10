package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.response.common_response.AlbumResponse;
import com.example.e_project_4_api.dto.response.display_response.AlbumDisplay;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Subjects;
import com.example.e_project_4_api.repositories.AlbumRepository;
import com.example.e_project_4_api.repositories.ArtistRepository;
import com.example.e_project_4_api.repositories.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<AlbumDisplay> getAllAlbumsForDisplay() {
        return repo.findAll()
                .stream()
                .map(this::toAlbumDisplay)
                .collect(Collectors.toList());
    }

    public AlbumResponse findById(int id) {
        Optional<Albums> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + id);
        }
        return toAlbumResponse(op.get());
    }
    public AlbumDisplay findDisplayById(int id) {
        Optional<Albums> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + id);
        }
        return toAlbumDisplay(op.get());
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
        if (request.getTitle().isEmpty() || (request.getTitle() == null)) {
            //check null
            errors.add("Title is required");
        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Albums> op = repo.findByTitle(request.getTitle());
            if (op.isPresent()) {
                errors.add("Already exist album with title: " + request.getTitle());
            }
        }
        //check null
        if (request.getImage().isEmpty() || (request.getImage() == null)) {
            errors.add("ImageURL is required");
        }
        //check sự tồn tại
        Optional<Artists> artist = artistRepo.findById(request.getArtistId());
        if (artist.isEmpty()) {
            errors.add("Can't find any artist with id: " + request.getArtistId());
        }
        //check sự tồn tại
        Optional<Subjects> subject = subjectRepo.findById(request.getSubjectId());
        if (subject.isEmpty()) {
            errors.add("Can't find any subject with id: " + request.getSubjectId());
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Albums newAlbum = new Albums(request.getTitle(), request.getImage(), false, request.getReleaseDate(),
                false, new Date(), new Date(), artist.get(), subject.get());
        repo.save(newAlbum);
        return request;
    }

    public NewOrUpdateAlbum updateAlbum(NewOrUpdateAlbum request) {
        List<String> errors = new ArrayList<>();

        Optional<Albums> op = repo.findById(request.getId());
        //check sự tồn tại
        if (op.isEmpty()) {
            errors.add("Can't find any album with id: " + request.getId());
        }
        if (request.getTitle().isEmpty() || (request.getTitle() == null)) {
            //check null
            errors.add("Title is required");
        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Albums> opTitle = repo.findByTitle(request.getTitle());
            if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
                errors.add("Already exist album with title: " + request.getTitle());
            }
        }
        //check null
        if (request.getImage().isEmpty() || (request.getImage() == null)) {
            errors.add("ImageURL is required");
        }
        //check sự tồn tại
        Optional<Artists> artist = artistRepo.findById(request.getArtistId());
        if (artist.isEmpty()) {
            errors.add("Can't find any artist with id: " + request.getArtistId());
        }
        //check sự tồn tại
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
        album.setIsReleased(request.getIsReleased());
        album.setArtistId(artist.get());
        album.setIsDeleted(request.getIsDeleted());
        album.setModifiedAt(new Date());
        repo.save(album);

        return request;
    }

    public AlbumResponse toAlbumResponse(Albums album) {
        AlbumResponse res = new AlbumResponse();
        BeanUtils.copyProperties(album, res);
        res.setIsDeleted(album.getIsDeleted());
        res.setIsReleased(album.getIsReleased());
        res.setArtistId(album.getArtistId().getId());
        res.setSubjectId(album.getSubjectId().getId());
        return res;
    }

    public AlbumDisplay toAlbumDisplay(Albums album) {
        AlbumDisplay res = new AlbumDisplay();
        BeanUtils.copyProperties(album, res);
        res.setIsDeleted(album.getIsDeleted());
        res.setIsReleased(album.getIsReleased());
        res.setArtistName(album.getArtistId().getArtistName());
        res.setArtistImage(album.getArtistId().getImage());
        res.setSubjectTitle(album.getSubjectId().getTitle());
        return res;
    }
}
