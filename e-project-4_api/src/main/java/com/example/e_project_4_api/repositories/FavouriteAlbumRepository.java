package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.FavouriteAlbums;
import com.example.e_project_4_api.models.FavouriteSongs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface FavouriteAlbumRepository extends JpaRepository<FavouriteAlbums, Integer> {
    @Query("SELECT fa FROM FavouriteAlbums fa WHERE fa.userId.id = :userId AND fa.albumId.id = :albumId")
    Optional<FavouriteAlbums> findByUserIdAndAlbumId(@Param("userId") Integer userId, @Param("albumId") Integer albumId);

    @Query("SELECT fa FROM FavouriteAlbums fa WHERE fa.userId.id = :userId AND fa.albumId.isDeleted = :isDeleted")
    Optional<FavouriteAlbums> findFAByUserId(@Param("userId") Integer userId, @Param("isDeleted") boolean isDeleted);
}