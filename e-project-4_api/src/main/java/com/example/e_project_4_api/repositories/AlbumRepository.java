package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Albums, Integer> {
    Optional<Albums> findByTitle(String title);

    @Query("Select a from Albums a where a.artistId.id = :arId")
    List<Albums> findAllByArtistId(@Param("arId") Integer artistId);
}
