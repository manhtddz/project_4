package com.example.e_project_4_api.dto.response.common_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAlbumResponse {

    private Integer id;
    private Integer albumId;
    private Integer categoryId;
    private Date createdAt;
    private Date modifiedAt;
}