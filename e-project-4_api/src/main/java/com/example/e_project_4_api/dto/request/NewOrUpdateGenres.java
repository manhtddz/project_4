package com.example.e_project_4_api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class NewOrUpdateGenres {
    private Integer id;
    private String title;
    private String image;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;

    public NewOrUpdateGenres(Integer id, String title, String image, Boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.isDeleted = isDeleted;
        this.createdAt = Date.from(Instant.now());
        this.modifiedAt = Date.from(Instant.now());
    }

}
