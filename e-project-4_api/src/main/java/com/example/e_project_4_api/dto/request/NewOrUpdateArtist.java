package com.example.e_project_4_api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class NewOrUpdateArtist {

    private Integer id;
    private String artistName;
    private String image;
    private String bio;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;

}
