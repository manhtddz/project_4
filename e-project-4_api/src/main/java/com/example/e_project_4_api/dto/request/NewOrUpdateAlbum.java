package com.example.e_project_4_api.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class NewOrUpdateAlbum {
    private Integer id;
    private String title;
    private String image;
    private Boolean isReleased;
    private Date releaseDate;
    private Integer artistId;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;


    public NewOrUpdateAlbum(Integer id, String title, String image, Boolean isReleased, Date releaseDate, Integer artistId, Boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.isReleased = isReleased;
        this.releaseDate = releaseDate;
        this.artistId = artistId;
        this.isDeleted = isDeleted;
        this.createdAt = Date.from(Instant.now());
        this.modifiedAt = Date.from(Instant.now());
    }

}
