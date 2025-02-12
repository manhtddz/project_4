package com.example.e_project_4_api.dto.response.display_for_admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordDisplayForAdmin implements Serializable {
    private Integer id;
    private String content;
    private Boolean isActive;
    private Date createdAt;
    private Date modifiedAt;


}
