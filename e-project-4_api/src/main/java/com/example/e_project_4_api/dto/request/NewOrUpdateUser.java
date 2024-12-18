package com.example.e_project_4_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NewOrUpdateUser {
    private Integer id;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "fullName is required")
    private String fullName;
    @NotBlank(message = "avatar is required")
    private String avatar;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "phone is required")
    private String phone;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "role is required")
    private String role;
    private String bio;
    @NotNull(message = "DOB is required")
    private Date dob;
    private Boolean isDeleted;
    @NotNull(message = "isActive is required")
    private Boolean isActive;
    private Date createdAt;
    private Date modifiedAt;


}
