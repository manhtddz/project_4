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
public class NewOrUpdateFavouriteSong {
    private Integer id;
    private Date createdAt;
    private Date modifiedAt;
    private Integer songId;
    private Integer userId;
}