package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
import com.example.e_project_4_api.dto.response.common_response.AlbumResponse;
import com.example.e_project_4_api.dto.response.display_response.AlbumDisplay;
import com.example.e_project_4_api.dto.response.display_response.SongDisplay;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.CategoryAlbum;
import com.example.e_project_4_api.models.FavouriteAlbums;
import com.example.e_project_4_api.repositories.*;
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
    private CategoryAlbumRepository categoryAlbumRepo;
    @Autowired
    private FavouriteAlbumRepository favRepo;

    public List<AlbumResponse> getAllAlbums() {
        return repo.findAll()
                .stream()
                .map(this::toAlbumResponse)
                .collect(Collectors.toList());
    }

    public List<AlbumDisplay> getAllAlbumsForDisplay() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toAlbumDisplay)
                .collect(Collectors.toList());
    }

    public List<AlbumDisplay> getAllAlbumsByArtistIdForDisplay(int artistId) {
        return repo.findAllByArtistId(artistId, false)
                .stream()
                .map(this::toAlbumDisplay)
                .collect(Collectors.toList());
    }

    public List<AlbumDisplay> getAllAlbumsBySubjectIdForDisplay(int cateId) {
        return categoryAlbumRepo.findAllByCategoryId(cateId, false)
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
        Optional<Albums> op = repo.findByIdAndIsDeleted(id, false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + id);
        }
        return toAlbumDisplay(op.get());
    }
    public List<AlbumDisplay> getAllFavAlbumsByUserId(Integer id) {
        return favRepo.findFAByUserId(id, false)
                .stream()
                .map(this::toAlbumDisplay)
                .collect(Collectors.toList());
    }
    public List<AlbumDisplay> search(String text) {
        return repo.findAll(AlbumSearchSpecifications.search(text))
                .stream()
                .map(this::toAlbumDisplay)
                .collect(Collectors.toList());
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
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<Albums> op = repo.findByTitle(request.getTitle());
        if (op.isPresent()) {
            errors.add(Map.of("titleError", "Already exist album with title: " + request.getTitle()));
        }

        Optional<Artists> artist = artistRepo.findByIdAndIsDeleted(request.getArtistId(), false);
        if (artist.isEmpty()) {
            errors.add(Map.of("artistError", "Can't find any artist with id: " + request.getArtistId()));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Albums newAlbum = new Albums(request.getTitle(), request.getImage(), false, request.getReleaseDate(),
                false, new Date(), new Date(), artist.get());
        repo.save(newAlbum);
        return request;
    }

    public NewOrUpdateAlbum updateAlbum(NewOrUpdateAlbum request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<Albums> op = repo.findByIdAndIsDeleted(request.getId(), false);
        //check sự tồn tại
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + request.getId());
        }

        Optional<Albums> opTitle = repo.findByTitle(request.getTitle());
        if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
            errors.add(Map.of("titleError", "Already exist album with title: " + request.getTitle()));
        }

        Optional<Artists> artist = artistRepo.findByIdAndIsDeleted(request.getArtistId(), false);
        if (artist.isEmpty()) {
            errors.add(Map.of("artistError", "Can't find any artist with id: " + request.getArtistId()));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Albums album = op.get();
        album.setTitle(request.getTitle());
        album.setImage(request.getImage());
        album.setReleaseDate(request.getReleaseDate());
        album.setIsReleased(request.getIsReleased());
        album.setArtistId(artist.get());
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
        return res;
    }

    public AlbumDisplay toAlbumDisplay(Albums album) {
        AlbumDisplay res = new AlbumDisplay();
        BeanUtils.copyProperties(album, res);
        res.setIsDeleted(album.getIsDeleted());
        res.setIsReleased(album.getIsReleased());
        res.setArtistName(album.getArtistId().getArtistName());
        res.setArtistImage(album.getArtistId().getImage());
        return res;
    }

    public AlbumDisplay toAlbumDisplay(CategoryAlbum categoryAlbum) {
        int albumId = categoryAlbum.getAlbumId().getId();
        Albums album = repo.findById(albumId).get();

        AlbumDisplay res = new AlbumDisplay();
        res.setTitle(album.getTitle());
        res.setImage(album.getImage());
        res.setReleaseDate(album.getReleaseDate());
        res.setIsDeleted(album.getIsDeleted());
        res.setIsReleased(album.getIsReleased());
        res.setArtistName(album.getArtistId().getArtistName());
        res.setArtistImage(album.getArtistId().getImage());
        res.setId(albumId);
        res.setCreatedAt(album.getCreatedAt());
        res.setModifiedAt(album.getModifiedAt());
        return res;
    }
    public AlbumDisplay toAlbumDisplay(FavouriteAlbums favAlbum) {
        int albumId = favAlbum.getAlbumId().getId();
        Albums album = repo.findById(albumId).get();

        AlbumDisplay res = new AlbumDisplay();
        res.setTitle(album.getTitle());
        res.setImage(album.getImage());
        res.setReleaseDate(album.getReleaseDate());
        res.setIsDeleted(album.getIsDeleted());
        res.setIsReleased(album.getIsReleased());
        res.setArtistName(album.getArtistId().getArtistName());
        res.setArtistImage(album.getArtistId().getImage());
        res.setId(albumId);
        res.setCreatedAt(album.getCreatedAt());
        res.setModifiedAt(album.getModifiedAt());
        return res;
    }
}
