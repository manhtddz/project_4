package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Integer> {
    @Query("SELECT ps FROM PlaylistSong ps WHERE ps.playlistId.id = :playlistId AND ps.songId.id = :songId")
    Optional<PlaylistSong> findByPlaylistIdAndSongId(@Param("playlistId") Integer playlistId, @Param("songId") Integer songId);
}
