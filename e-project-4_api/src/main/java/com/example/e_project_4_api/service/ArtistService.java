package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateArtist;
import com.example.e_project_4_api.dto.response.common_response.ArtistResponse;
import com.example.e_project_4_api.dto.response.display_for_admin.ArtistDisplayForAdmin;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Songs;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.ArtistRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository repo;
    @Autowired
    private UserRepository userRepo;

    @Cacheable("artistsDisplay")
    public List<ArtistResponse> getAllArtists() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toArtistResponse)
                .collect(Collectors.toList());
    }

    @Cacheable("artistsDisplayForAdmin")
    public List<ArtistDisplayForAdmin> getAllArtistsDisplayForAdmin() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toArtistDisplayForAdmin)
                .collect(Collectors.toList());
    }


    public ArtistResponse findById(int id) {
        Optional<Artists> op = repo.findByIdAndIsDeleted(id, false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any artist with id: " + id);
        }
        return toArtistResponse(op.get());
    }

    public ArtistDisplayForAdmin findByIdForAdmin(int id) {
        Optional<Artists> op = repo.findByIdAndIsDeleted(id, false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any artist with id: " + id);
        }
        return toArtistDisplayForAdmin(op.get());
    }

    @CacheEvict(value = {"artistsDisplayForAdmin", "artistsDisplay"})
    public boolean deleteById(int id) {
        Optional<Artists> artistOptional = repo.findById(id);
        if (artistOptional.isEmpty()) {
            throw new NotFoundException("Can't find any artist with id: " + id);
        }
        Artists artist = artistOptional.get();
        artist.setIsDeleted(true);
        repo.save(artist);
        return true;
    }

    @CacheEvict(value = {"artistsDisplayForAdmin", "artistsDisplay"})
    public NewOrUpdateArtist addNewArtist(NewOrUpdateArtist request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<Artists> op = repo.findByArtistName(request.getArtistName());
        if (op.isPresent()) {
            errors.add(Map.of("artistNameError", "Already exist artist name"));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Artists newArtist = new Artists(request.getArtistName(), request.getImage(),
                request.getBio(), false, new Date(), new Date());
        repo.save(newArtist);
        return request;
    }

    @CacheEvict(value = {"artistsDisplayForAdmin", "artistsDisplay"})
    public NewOrUpdateArtist updateArtist(NewOrUpdateArtist request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<Artists> op = repo.findByIdAndIsDeleted(request.getId(), false);

        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any artist with id: " + request.getId());
        }
        Artists artist = op.get();

        if (request.getUserId() != null) {
            Optional<Users> userOp = userRepo.findByIdAndIsDeleted(request.getUserId(), false);
            if (userOp.isEmpty()) {
                errors.add(Map.of("userNotExistedError", "Can't find user"));
            }
            Users foundUser = userOp.get();
            Optional<Artists> foundArtistWithUID = repo.findByUserId(request.getUserId(), false);
            if (foundArtistWithUID.isPresent()) {
                errors.add(Map.of("accountAssignedError", "This artist account already have owner"));
            }
            artist.setUserId(foundUser);
        }

        Optional<Artists> opName = repo.findByArtistName(request.getArtistName());
        if (opName.isPresent() && opName.get().getArtistName() != op.get().getArtistName()) {
            errors.add(Map.of("artistNameError", "Already exist artist name"));
        }


        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        artist.setArtistName(request.getArtistName());
        artist.setImage(request.getImage());
        artist.setBio(request.getBio());
        artist.setModifiedAt(new Date());
        repo.save(artist);

        return request;
    }


    public ArtistResponse toArtistResponse(Artists artist) {
        ArtistResponse res = new ArtistResponse();
        BeanUtils.copyProperties(artist, res);
        res.setIsDeleted(artist.getIsDeleted());
        return res;
    }

    public ArtistDisplayForAdmin toArtistDisplayForAdmin(Artists artist) {
        ArtistDisplayForAdmin res = new ArtistDisplayForAdmin();
        BeanUtils.copyProperties(artist, res);
        res.setIsDeleted(artist.getIsDeleted());
        res.setTotalSong(artist.getSongsCollection().size());
        res.setTotalAlbum(artist.getAlbumsCollection().size());
        int totalListenAmount = artist.getSongsCollection()
                .stream().mapToInt(Songs::getListenAmount).sum();
        res.setTotalListenAmount(totalListenAmount);
        Users user = artist.getUserId();
        if (user != null) {
            res.setUsername(artist.getUserId().getUsername());
            res.setIsActive(true);
            return res;
        }
        res.setUsername("");
        res.setIsActive(false);
        return res;
    }
}
