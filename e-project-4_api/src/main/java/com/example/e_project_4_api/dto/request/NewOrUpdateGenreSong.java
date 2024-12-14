package com.example.e_project_4_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewOrUpdateGenreSong {
    private Integer id;
    private Integer genreId;
    private Integer songId;
    private Date createdAt;
    private Date modifiedAt;


}
