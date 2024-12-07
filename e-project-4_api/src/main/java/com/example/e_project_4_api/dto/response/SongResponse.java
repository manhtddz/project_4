/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.e_project_4_api.dto.response;

import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.GenreSong;
import com.example.e_project_4_api.models.PlaylistSong;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


public class SongResponse{


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
    private Integer artistId;

    public SongResponse() {
    }

    public SongResponse(Integer id, String title, String audioPath, Integer likeAmount, Integer amount, String lyricFilePath,
                        Boolean isPending, Boolean isDeleted, Date createdAt, Date modifiedAt, Integer albumId,Integer artistId) {
        this.id = id;
        this.title = title;
        this.audioPath = audioPath;
        this.amount = amount;
        this.likeAmount = likeAmount;
        this.lyricFilePath = lyricFilePath;
        this.isPending = isPending;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.albumId = albumId;
        this.artistId = artistId;
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

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }
}
