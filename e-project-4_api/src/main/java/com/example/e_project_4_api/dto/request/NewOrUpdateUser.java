package com.example.e_project_4_api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class NewOrUpdateUser {
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

    public NewOrUpdateUser(Integer id, String username, String fullName, String avatar, String password, String phone, String email, String role, String bio, Date dob, Boolean isActive, Boolean isDeleted, Date createdAt, Date modifiedAt, Integer artistId) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.avatar = avatar;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.bio = bio;
        this.dob = dob;
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.createdAt = Date.from(Instant.now());
        this.modifiedAt = Date.from(Instant.now());
        this.artistId = artistId;
    }
}
