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
    @Column(name = "like_amount")
    private Integer likeAmount;
    @Column(name = "listen_amount")
    private Integer listenAmount;
    @Column(name = "feature_artist")
    private String featureArtist;
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
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    @ManyToOne
    private Artists artistId;
    @OneToMany(mappedBy = "songId")
    private Collection<FavouriteSongs> favouriteSongsCollection;
    @OneToMany(mappedBy = "songId")
    private Collection<PlaylistSong> playlistSongCollection;
    @OneToMany(mappedBy = "songId")
    private Collection<GenreSong> genreSongCollection;

    public Songs() {
    }

    public Songs(Integer id, String title, String audioPath, Integer likeAmount, Integer listenAmount, String featureArtist, String lyricFilePath, Boolean isPending, Boolean isDeleted, Date createdAt, Date modifiedAt, Albums albumId, Artists artistId) {
        this.id = id;
        this.title = title;
        this.audioPath = audioPath;
        this.likeAmount = likeAmount;
        this.listenAmount = listenAmount;
        this.featureArtist = featureArtist;
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

    public Integer getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(Integer likeAmount) {
        this.likeAmount = likeAmount;
    }

    public Integer getListenAmount() {
        return listenAmount;
    }

    public void setListenAmount(Integer listenAmount) {
        this.listenAmount = listenAmount;
    }

    public String getFeatureArtist() {
        return featureArtist;
    }

    public void setFeatureArtist(String featureArtist) {
        this.featureArtist = featureArtist;
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

    public Artists getArtistId() {
        return artistId;
    }

    public void setArtistId(Artists artistId) {
        this.artistId = artistId;
    }

    public Collection<FavouriteSongs> getFavouriteSongsCollection() {
        return favouriteSongsCollection;
    }

    public void setFavouriteSongsCollection(Collection<FavouriteSongs> favouriteSongsCollection) {
        this.favouriteSongsCollection = favouriteSongsCollection;
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
