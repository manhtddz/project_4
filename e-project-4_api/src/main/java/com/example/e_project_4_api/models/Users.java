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
import jakarta.persistence.Lob;
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
@Table(name = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "full_name")
    private String fullName;
    @Basic(optional = false)
    @Column(name = "avatar")
    private String avatar;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private String role;
    @Lob
    @Column(name = "bio")
    private String bio;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
    @OneToMany(mappedBy = "userId")
    private Collection<FavouriteSongs> favouriteSongsCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Playlists> playlistsCollection;
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    @ManyToOne
    private Artists artistId;

    public Users() {
    }

    public Users(String username, String fullName, String avatar, String password, String phone, String email, String role, String bio, Date dob, Boolean isDeleted, Date createdAt, Date modifiedAt, Artists artistId) {

        this.username = username;
        this.fullName = fullName;
        this.avatar = avatar;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.bio = bio;
        this.dob = dob;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.artistId = artistId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public Collection<FavouriteSongs> getFavouriteSongsCollection() {
        return favouriteSongsCollection;
    }

    public void setFavouriteSongsCollection(Collection<FavouriteSongs> favouriteSongsCollection) {
        this.favouriteSongsCollection = favouriteSongsCollection;
    }

    public Collection<Playlists> getPlaylistsCollection() {
        return playlistsCollection;
    }

    public void setPlaylistsCollection(Collection<Playlists> playlistsCollection) {
        this.playlistsCollection = playlistsCollection;
    }

    public Artists getArtistId() {
        return artistId;
    }

    public void setArtistId(Artists artistId) {
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Users[ id=" + id + " ]";
    }
    
}
