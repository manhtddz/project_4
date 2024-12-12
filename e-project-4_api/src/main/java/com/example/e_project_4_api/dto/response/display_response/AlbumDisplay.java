package com.example.e_project_4_api.dto.response.display_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AlbumDisplay {
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

}
