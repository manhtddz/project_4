package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keywords, Integer> {
    Optional<Keywords> findByContent(String content);

    @Query("Select COUNT(a) from Keywords a")
    int getNumberOfAll();
}