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
public class NewOrUpdateSubjectAlbum {

    private Integer id;
    private Boolean isDeleted;
    private Integer albumId;
    private Integer subjectId;
    private Date createdAt;
    private Date modifiedAt;
}
