package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.ArtistRequest;
import com.example.e_project_4_api.dto.response.ArtistResponse;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    // 1. Create or Update an Artist
    public ArtistResponse createOrUpdateArtist(ArtistRequest artistRequest) {
        Artists artist = new Artists();
        artist.setArtistName(artistRequest.getArtistName());
        artist.setImage(artistRequest.getImage());
        artist.setBio(artistRequest.getBio());
        artist.setIsDeleted(artistRequest.getIsDeleted());
        artist.setCreatedAt(artistRequest.getCreatedAt() != null ? artistRequest.getCreatedAt() : new Date());
        artist.setModifiedAt(new Date());

        artist = artistRepository.save(artist);

        return convertToResponse(artist);
    }

    // 2. Update Artist by ID
    public ArtistResponse updateArtist(Integer id, ArtistRequest artistRequest) {
        Optional<Artists> artistOpt = artistRepository.findById(id);
        if (artistOpt.isPresent()) {
            Artists artist = artistOpt.get();
            artist.setArtistName(artistRequest.getArtistName());
            artist.setImage(artistRequest.getImage());
            artist.setBio(artistRequest.getBio());
            artist.setIsDeleted(artistRequest.getIsDeleted());
            artist.setModifiedAt(new Date());

            artist = artistRepository.save(artist);
            return convertToResponse(artist);
        }
        return null;
    }

    // 3. Get Artist by ID
    public ArtistResponse getArtistById(Integer id) {
        Optional<Artists> artistOpt = artistRepository.findById(id);
        return artistOpt.map(this::convertToResponse).orElse(null);
    }

    // 4. Get All Artists
    public List<ArtistResponse> getAllArtists() {
        List<Artists> artists = artistRepository.findAll();
        return artists.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    // 5. Delete Artist by ID
    public boolean deleteArtist(Integer id) {
        Optional<Artists> artistOpt = artistRepository.findById(id);
        if (artistOpt.isPresent()) {
            artistRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper method to convert entity to DTO
    private ArtistResponse convertToResponse(Artists artist) {
        ArtistResponse response = new ArtistResponse();
        response.setId(artist.getId());
        response.setArtistName(artist.getArtistName());
        response.setImage(artist.getImage());
        response.setBio(artist.getBio());
        response.setIsDeleted(artist.getIsDeleted());
        response.setCreatedAt(artist.getCreatedAt());
        response.setModifiedAt(artist.getModifiedAt());
        return response;
    }
}
