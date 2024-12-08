package com.example.e_project_4_api.dto.request;

import java.util.Date;

public class NewOrUpdateGenres {
    private Integer id;
    private String title;
    private String image;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;

    public NewOrUpdateGenres(Integer id, String title, String image, Boolean isDeleted, Date createdAt, Date modifiedAt) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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
