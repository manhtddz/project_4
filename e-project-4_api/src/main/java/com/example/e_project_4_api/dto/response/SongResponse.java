/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.e_project_4_api.dto.response;

import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.GenreSong;
import com.example.e_project_4_api.models.PlaylistSong;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

}
