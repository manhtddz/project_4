package com.example.e_project_4_api.dto.response;

import java.util.Date;

public class AlbumResponse {
    private Integer id;
    private String title;
    private String image;
    private Integer artistId;
    private Date releaseDate;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;

    public AlbumResponse(Integer id, String title, String image, Integer artistId, Date releaseDate, Boolean isDeleted, Date createdAt, Date modifiedAt) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public AlbumResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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
