package com.example.e_project_4_api.dto.request;

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
    private String title;
    private String audioPath;
    private Integer listenAmount;
    private String lyricFilePath;
    private String featureArtist;
    private Boolean isPending;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
    private Integer albumId;
    private Integer artistId;

}
