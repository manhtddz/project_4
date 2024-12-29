package com.example.e_project_4_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NewOrUpdateArtist {

    private Integer id;
    @NotBlank(message = "artistName is required")
    private String artistName;
    @NotBlank(message = "image is required")
    private String image;
    private String bio;
    @NotNull(message = "userId is required")
    private Integer userId;

}
