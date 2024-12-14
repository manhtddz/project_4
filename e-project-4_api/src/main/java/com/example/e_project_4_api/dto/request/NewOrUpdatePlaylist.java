package com.example.e_project_4_api.dto.request;

import com.example.e_project_4_api.models.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NewOrUpdatePlaylist {
    private Integer id;
    private String title;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
    private Integer userId;


}
