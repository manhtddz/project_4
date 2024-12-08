package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.GenreSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreSongRepository extends JpaRepository<GenreSong, Integer> {
}
