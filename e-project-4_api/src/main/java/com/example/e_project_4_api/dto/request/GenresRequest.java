package com.example.e_project_4_api.dto.request;

import java.util.Date;

public class GenresRequest {

    private String title;
    private String image;
    private Boolean isDeleted;

    // Getter và Setter

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

    @Override
    public String toString() {
        return "GenresRequestDTO{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
