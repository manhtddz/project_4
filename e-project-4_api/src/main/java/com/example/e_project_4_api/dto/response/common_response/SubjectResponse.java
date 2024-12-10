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
public class SubjectResponse {
    private Integer id;
    private String title;
    private String image;
    private String description;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
}
