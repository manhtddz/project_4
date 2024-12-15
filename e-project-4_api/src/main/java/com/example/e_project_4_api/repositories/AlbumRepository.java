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

    Optional<Albums> findByIdAndIsDeleted(Integer id, boolean isDeleted);

    @Query("Select a from Albums a where a.artistId.id = :arId AND a.isDeleted = :isDeleted")
    List<Albums> findAllByArtistId(@Param("arId") Integer artistId, @Param("isDeleted") boolean isDeleted);

    @Query("Select a from Albums a where a.isDeleted = :isDeleted")
    List<Albums> findAllNotDeleted(@Param("isDeleted") boolean isDeleted);


}
