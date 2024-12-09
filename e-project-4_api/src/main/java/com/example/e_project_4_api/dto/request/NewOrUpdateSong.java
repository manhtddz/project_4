package com.example.e_project_4_api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class NewOrUpdateSong {
    private Integer id;
    private String title;
    private String audioPath;
    private Integer listenAmount;
    private Integer likeAmount;
    private String lyricFilePath;
    private String featureArtist;
    private Boolean isPending;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
    private Integer albumId;
    private Integer artistId;


    public NewOrUpdateSong(Integer id, String title, String audioPath, Integer listenAmount, Integer likeAmount, String lyricFilePath, String featureArtist, Boolean isPending, Boolean isDeleted, Integer albumId, Integer artistId) {
        this.id = id;
        this.title = title;
        this.audioPath = audioPath;
        this.listenAmount = listenAmount;
        this.likeAmount = likeAmount;
        this.lyricFilePath = lyricFilePath;
        this.featureArtist = featureArtist;
        this.isPending = isPending;
        this.isDeleted = isDeleted;
        this.createdAt = Date.from(Instant.now());
        this.modifiedAt = Date.from(Instant.now());
        this.albumId = albumId;
        this.artistId = artistId;
    }
}
