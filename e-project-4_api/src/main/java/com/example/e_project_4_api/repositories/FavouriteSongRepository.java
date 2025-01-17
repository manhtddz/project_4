package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.FavouriteSongs;
import com.example.e_project_4_api.models.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface FavouriteSongRepository extends JpaRepository<FavouriteSongs,Integer> {
    @Query("SELECT fs FROM FavouriteSongs fs WHERE fs.userId.id = :userId AND fs.songId.id = :songId")
    Optional<FavouriteSongs> findByUserIdAndSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);
}