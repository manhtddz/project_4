/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.e_project_4_api.models;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "genre_song")
@NamedQueries({
    @NamedQuery(name = "GenreSong.findAll", query = "SELECT g FROM GenreSong g"),
    @NamedQuery(name = "GenreSong.findById", query = "SELECT g FROM GenreSong g WHERE g.id = :id"),
    @NamedQuery(name = "GenreSong.findByIsDeleted", query = "SELECT g FROM GenreSong g WHERE g.isDeleted = :isDeleted"),
    @NamedQuery(name = "GenreSong.findByCreatedAt", query = "SELECT g FROM GenreSong g WHERE g.createdAt = :createdAt"),
    @NamedQuery(name = "GenreSong.findByModifiedAt", query = "SELECT g FROM GenreSong g WHERE g.modifiedAt = :modifiedAt")})
public class GenreSong implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    @ManyToOne
    private Genres genreId;
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    @ManyToOne
    private Songs songId;

    public GenreSong() {
    }

    public GenreSong(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public Genres getGenreId() {
        return genreId;
    }

    public void setGenreId(Genres genreId) {
        this.genreId = genreId;
    }

    public Songs getSongId() {
        return songId;
    }

    public void setSongId(Songs songId) {
        this.songId = songId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenreSong)) {
            return false;
        }
        GenreSong other = (GenreSong) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.GenreSong[ id=" + id + " ]";
    }
    
}
