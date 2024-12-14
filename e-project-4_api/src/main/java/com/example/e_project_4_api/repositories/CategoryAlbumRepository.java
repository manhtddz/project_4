package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.CategoryAlbum;
import com.example.e_project_4_api.models.GenreSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryAlbumRepository extends JpaRepository<CategoryAlbum, Integer> {


    @Query("SELECT sa FROM CategoryAlbum sa WHERE sa.categoryId.id = :categoryId AND sa.albumId.id = :albumId")
    Optional<CategoryAlbum> findByCategoryIdAndAlbumId(@Param("categoryId") Integer categoryId, @Param("albumId") Integer albumId);

    @Query("SELECT sa FROM CategoryAlbum sa WHERE sa.categoryId.id = :categoryId")
    List<CategoryAlbum> findAllByCategoryId(@Param("categoryId") Integer categoryId);
}