package com.example.e_project_4_api.dto.request;

import java.time.Instant;
import java.util.Date;

public class NewOrUpdateSong {
    private Integer id;
    private String title;
    private String audioPath;
    private Integer amount;
    private Integer likeAmount;
    private String lyricFilePath;
    private Boolean isPending;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
    private Integer albumId;

    public NewOrUpdateSong() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(Integer likeAmount) {
        this.likeAmount = likeAmount;
    }

    public String getLyricFilePath() {
        return lyricFilePath;
    }

    public void setLyricFilePath(String lyricFilePath) {
        this.lyricFilePath = lyricFilePath;
    }

    public Boolean getPending() {
        return isPending;
    }

    public void setPending(Boolean pending) {
        isPending = pending;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public NewOrUpdateSong(Integer id, String title, String audioPath, Integer amount, Integer likeAmount, String lyricFilePath
            , Boolean isPending, Boolean isDeleted, Integer albumId) {
        this.id = id;
        this.title = title;
        this.audioPath = audioPath;
        this.amount = amount;
        this.likeAmount = likeAmount;
        this.lyricFilePath = lyricFilePath;
        this.isPending = isPending;
        this.isDeleted = isDeleted;
        this.createdAt = Date.from(Instant.now());
        this.modifiedAt = Date.from(Instant.now());
        this.albumId = albumId;
    }
}
