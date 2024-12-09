package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.FavouriteSongs;
import com.example.e_project_4_api.models.GenreSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreSongRepository extends JpaRepository<GenreSong, Integer> {
    @Query("SELECT gs FROM GenreSong gs WHERE gs.genreId.id = :genreId AND gs.songId.id = :songId")
    Optional<GenreSong> findByGenreIdAndSongId(@Param("genreId") Integer genreId, @Param("songId") Integer songId);
}
