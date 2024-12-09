package com.example.e_project_4_api.dto.request;

import com.example.e_project_4_api.models.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class NewOrUpdatePlaylist {
    private Integer id;
    private String title;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
    private Integer userId;


    public NewOrUpdatePlaylist(Integer id, String title, Boolean isDeleted, Integer userId) {
        this.id = id;
        this.title = title;
        this.isDeleted = isDeleted;
        this.createdAt = Date.from(Instant.now());
        this.modifiedAt = Date.from(Instant.now());
        this.userId = userId;
    }
}
