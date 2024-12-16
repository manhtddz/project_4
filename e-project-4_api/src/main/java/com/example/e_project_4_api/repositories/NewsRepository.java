package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Genres;
import com.example.e_project_4_api.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    Optional<News> findByTitle(String title);

}
