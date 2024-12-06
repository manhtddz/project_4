/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.e_project_4_api.models;

import java.io.Serializable;
import java.util.Collection;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "songs")
@NamedQueries({
    @NamedQuery(name = "Songs.findAll", query = "SELECT s FROM Songs s"),
    @NamedQuery(name = "Songs.findById", query = "SELECT s FROM Songs s WHERE s.id = :id"),
    @NamedQuery(name = "Songs.findByTitle", query = "SELECT s FROM Songs s WHERE s.title = :title"),
    @NamedQuery(name = "Songs.findByAudioPath", query = "SELECT s FROM Songs s WHERE s.audioPath = :audioPath"),
    @NamedQuery(name = "Songs.findByAmount", query = "SELECT s FROM Songs s WHERE s.amount = :amount"),
    @NamedQuery(name = "Songs.findByLyricFilePath", query = "SELECT s FROM Songs s WHERE s.lyricFilePath = :lyricFilePath"),
    @NamedQuery(name = "Songs.findByIsPending", query = "SELECT s FROM Songs s WHERE s.isPending = :isPending"),
    @NamedQuery(name = "Songs.findByIsDeleted", query = "SELECT s FROM Songs s WHERE s.isDeleted = :isDeleted"),
    @NamedQuery(name = "Songs.findByCreatedAt", query = "SELECT s FROM Songs s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Songs.findByModifiedAt", query = "SELECT s FROM Songs s WHERE s.modifiedAt = :modifiedAt")})
public class Songs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Column(name = "audio_path")
    private String audioPath;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "like_amount")
    private Integer likeAmount;
    @Column(name = "lyric_file_path")
    private String lyricFilePath;
    @Column(name = "is_pending")
    private Boolean isPending;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    @ManyToOne
    private Albums albumId;
    @OneToMany(mappedBy = "songId")
    private Collection<SubArtist> subArtistCollection;
    @OneToMany(mappedBy = "songId")
    private Collection<PlaylistSong> playlistSongCollection;
    @OneToMany(mappedBy = "songId")
    private Collection<GenreSong> genreSongCollection;

    public Songs() {
    }

    public Songs(Integer id, String title, String audioPath, Integer amount, Integer likeAmount, String lyricFilePath,
                 Boolean isPending, Boolean isDeleted, Date createdAt, Date modifiedAt, Albums albumId) {
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
    }

    public Integer getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(Integer likeAmount) {
        this.likeAmount = likeAmount;
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

    public String getLyricFilePath() {
        return lyricFilePath;
    }

    public void setLyricFilePath(String lyricFilePath) {
        this.lyricFilePath = lyricFilePath;
    }

    public Boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(Boolean isPending) {
        this.isPending = isPending;
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

    public Albums getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Albums albumId) {
        this.albumId = albumId;
    }

    public Collection<SubArtist> getSubArtistCollection() {
        return subArtistCollection;
    }

    public void setSubArtistCollection(Collection<SubArtist> subArtistCollection) {
        this.subArtistCollection = subArtistCollection;
    }

    public Collection<PlaylistSong> getPlaylistSongCollection() {
        return playlistSongCollection;
    }

    public void setPlaylistSongCollection(Collection<PlaylistSong> playlistSongCollection) {
        this.playlistSongCollection = playlistSongCollection;
    }

    public Collection<GenreSong> getGenreSongCollection() {
        return genreSongCollection;
    }

    public void setGenreSongCollection(Collection<GenreSong> genreSongCollection) {
        this.genreSongCollection = genreSongCollection;
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
        if (!(object instanceof Songs)) {
            return false;
        }
        Songs other = (Songs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Songs[ id=" + id + " ]";
    }
    
}
