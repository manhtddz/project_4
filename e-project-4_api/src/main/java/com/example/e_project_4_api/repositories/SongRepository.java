package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Songs, Integer> {
    Optional<Songs> findByTitle(String title);

    @Query("Select a from Songs a where a.artistId.id = :arId")
    List<Songs> findAllByArtistId(@Param("arId") Integer artistId);

    @Query("Select a from Songs a where a.albumId.id = :alId")
    List<Songs> findAllByAlbumId(@Param("alId") Integer albumId);
}
