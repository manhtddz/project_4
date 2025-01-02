package com.example.e_project_4_api.dto.response.display_for_admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AlbumDisplayForAdmin implements Serializable {
    private Integer id;
    private String title;
    private String image;
    private Boolean isReleased;
    private Date releaseDate;
    private String artistName;
    private String artistImage;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
    private Integer totalSong;//field thiếu
    private Integer totalFavourite;//field thiếu
}