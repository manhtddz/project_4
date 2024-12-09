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
public class NewOrUpdateSubject {
    private Integer id;
    private String title;
    private String image;
    private String description;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
}
