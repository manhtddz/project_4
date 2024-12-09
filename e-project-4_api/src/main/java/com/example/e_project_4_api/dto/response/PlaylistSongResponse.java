/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.e_project_4_api.dto.response;

import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Playlists;
import com.example.e_project_4_api.models.Songs;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSongResponse {
    private Integer id;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
    private Integer playlistId;
    private Integer songId;


}
