package com.example.e_project_4_api.dto.response;

import java.util.Date;

public class GenreSongResponse {
    private Integer id;
    private Integer genreId;
    private Integer songId;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;


    public GenreSongResponse(Integer id, Integer genreId, Integer songId, Boolean isDeleted, Date createdAt, Date modifiedAt) {
        this.id = id;
        this.genreId = genreId;
        this.songId = songId;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public GenreSongResponse() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
