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

public class UserResponse {

    private Integer id;
    private String username;
    private String fullName;
    private String avatar;
    private String password;
    private String phone;
    private String email;
    private String role;
    private String bio;
    private Date dob;
    private Boolean isDeleted;
    private Boolean isActive;
    private Date createdAt;
    private Date modifiedAt;

}
