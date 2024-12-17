/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.e_project_4_api.dto.response.display_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SongDisplay {


    private Integer id;
    private String title;
    private String audioPath;
    private Integer likeAmount;
    private String lyricFilePath;
    private Boolean isPending;
    private Boolean isDeleted;
    private Date createdAt;
    private Date modifiedAt;
    private String albumTilte;
    private String albumImage;
    private String artistName;

}
