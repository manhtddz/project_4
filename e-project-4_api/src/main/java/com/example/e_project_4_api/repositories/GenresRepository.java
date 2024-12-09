package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Integer> {

    Optional<Genres> findByTitle(String title);
}
