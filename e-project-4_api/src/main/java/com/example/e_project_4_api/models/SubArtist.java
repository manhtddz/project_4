/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.e_project_4_api.models;

import java.io.Serializable;
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

/**
 *
 * @author admin
 */
@Entity
@Table(name = "sub_artist")
@NamedQueries({
    @NamedQuery(name = "SubArtist.findAll", query = "SELECT s FROM SubArtist s"),
    @NamedQuery(name = "SubArtist.findById", query = "SELECT s FROM SubArtist s WHERE s.id = :id")})
public class SubArtist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    @ManyToOne
    private Songs songId;
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    @ManyToOne
    private Users artistId;

    public SubArtist() {
    }

    public SubArtist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Songs getSongId() {
        return songId;
    }

    public void setSongId(Songs songId) {
        this.songId = songId;
    }

    public Users getArtistId() {
        return artistId;
    }

    public void setArtistId(Users artistId) {
        this.artistId = artistId;
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
        if (!(object instanceof SubArtist)) {
            return false;
        }
        SubArtist other = (SubArtist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.SubArtist[ id=" + id + " ]";
    }
    
}
