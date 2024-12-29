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
public class NewOrUpdateSong {
    private Integer id;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "audioPath is required")
    private String audioPath;
    private Integer listenAmount;
    @NotBlank(message = "lyricFilePath is required")
    private String lyricFilePath;
    @NotBlank(message = "featureArtist is required")
    private String featureArtist;
    @NotNull(message = "isPending is required")
    private Boolean isPending;
    @NotNull(message = "albumId is required")
    private Integer albumId;
    @NotNull(message = "artistId is required")
    private Integer artistId;

}
