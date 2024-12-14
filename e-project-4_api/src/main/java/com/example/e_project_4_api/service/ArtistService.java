package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateArtist;
import com.example.e_project_4_api.dto.response.common_response.ArtistResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.ArtistRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository repo;
    @Autowired
    private UserRepository userRepo;


    public List<ArtistResponse> getAllArtists() {
        return repo.findAll()
                .stream()
                .map(this::toArtistResponse)
                .collect(Collectors.toList());
    }


    public ArtistResponse findById(int id) {
        Optional<Artists> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any artist with id: " + id);
        }
        return toArtistResponse(op.get());
    }


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


    public NewOrUpdateArtist addNewArtist(NewOrUpdateArtist request) {
        List<String> errors = new ArrayList<>();


        if (request.getArtistName() == null || request.getArtistName().isEmpty()) {
            errors.add("Artist name is required");
        } else {
            Optional<Artists> op = repo.findByArtistName(request.getArtistName());
            if (op.isPresent()) {
                errors.add("Already exist artist with name: " + request.getArtistName());
            }
        }


        if (request.getImage() == null || request.getImage().isEmpty()) {
            errors.add("ImageURL is required");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Artists newArtist = new Artists(request.getArtistName(), request.getImage(),
                request.getBio(), false, new Date(), new Date());
        repo.save(newArtist);
        return request;
    }


    public NewOrUpdateArtist updateArtist(NewOrUpdateArtist request) {
        List<String> errors = new ArrayList<>();

        Optional<Artists> op = repo.findById(request.getId());

        if (op.isEmpty()) {
            errors.add("Can't find any artist with id: " + request.getId());
        }
        Artists artist = op.get();

        if (request.getUserId() != null) {
            Optional<Users> userOp = userRepo.findById(request.getUserId());
            if(userOp.isEmpty()){
                throw new NotFoundException("Can't find any user with id: " + request.getUserId());
            }
            Users foundUser =  userOp.get();
            Optional<Artists> foundArtistWithUID = repo.findByUserId(request.getUserId());
            if(foundArtistWithUID.isPresent()){
                throw new AlreadyExistedException("This artist account already have owner");
            }
            artist.setUserId(foundUser);
        }
        if (request.getArtistName() == null || request.getArtistName().isEmpty()) {
            errors.add("Artist name is required");
        } else {
            Optional<Artists> opName = repo.findByArtistName(request.getArtistName());
            if (opName.isPresent() && opName.get().getArtistName() != op.get().getArtistName()) {
                errors.add("Already exist artist with name: " + request.getArtistName());
            }
        }

        if (request.getImage() == null || request.getImage().isEmpty()) {
            errors.add("ImageURL is required");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        artist.setArtistName(request.getArtistName());
        artist.setImage(request.getImage());
        artist.setBio(request.getBio());
        artist.setIsDeleted(request.getIsDeleted());
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
}
