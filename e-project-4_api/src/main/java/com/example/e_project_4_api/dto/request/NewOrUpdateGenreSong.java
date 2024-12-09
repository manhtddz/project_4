package com.example.e_project_4_api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class NewOrUpdateGenreSong {
    private Integer id;
    private Integer genreId;
    private Integer songId;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;




    public NewOrUpdateGenreSong(Integer id, Integer genreId, Integer songId, Boolean isDeleted, Date createdAt, Date modifiedAt) {
        this.id = id;
        this.genreId = genreId;
        this.songId = songId;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
