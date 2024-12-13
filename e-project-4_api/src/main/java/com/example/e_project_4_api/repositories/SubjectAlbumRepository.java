package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.GenreSong;
import com.example.e_project_4_api.models.SubjectAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectAlbumRepository extends JpaRepository<SubjectAlbum, Integer> {


    @Query("SELECT sa FROM SubjectAlbum sa WHERE sa.subjectId.id = :subjectId AND sa.albumId.id = :albumId")
    Optional<SubjectAlbum> findBySubjectIdAndAlbumId(@Param("subjectId") Integer subjectId, @Param("albumId") Integer albumId);

    @Query("SELECT sa FROM SubjectAlbum sa WHERE sa.subjectId.id = :subjectId")
    List<SubjectAlbum> findAllBySubjectId(@Param("subjectId") Integer subjectId);
}
