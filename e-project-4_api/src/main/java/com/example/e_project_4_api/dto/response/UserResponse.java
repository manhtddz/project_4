package com.example.e_project_4_api.dto.response;

import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.FavouriteSongs;
import com.example.e_project_4_api.models.Playlists;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserResponse {

    private Integer id;
    private String username;
    private String fullName;
    private String avatar;
    private String password;
    private String phone;
    private String email;
    private String role;
    private String bio;
    private Date dob;
    private Boolean isDeleted;
    private Boolean isActive;
    private Date createdAt;
    private Date modifiedAt;
    private Integer artistId;

}
