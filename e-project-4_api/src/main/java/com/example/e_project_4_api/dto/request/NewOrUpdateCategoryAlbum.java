package com.example.e_project_4_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewOrUpdateCategoryAlbum {

    private Integer id;
    private Integer albumId;
    private Integer categoryId;
    private Date createdAt;
    private Date modifiedAt;
}
