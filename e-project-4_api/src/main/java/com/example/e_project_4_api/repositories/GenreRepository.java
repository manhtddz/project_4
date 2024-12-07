package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genres, Integer> {
    // Các truy vấn tuỳ chỉnh (nếu cần) có thể được thêm vào đây
}
