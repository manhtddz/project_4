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

public class NewOrUpdateAlbum {
    private Integer id;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "image is required")
    private String image;
    private Boolean isReleased;
    @NotNull(message = "releaseDate is required")
    private Date releaseDate;
    @NotNull(message = "artistId is required")
    private Integer artistId;


}
