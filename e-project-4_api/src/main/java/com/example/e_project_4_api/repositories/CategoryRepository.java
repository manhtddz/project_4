package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories,Integer>{
    Optional<Categories> findByTitle(String title);

    Optional<Categories> findByIdAndIsDeleted(Integer id, boolean isDeleted);

    @Query("Select a from Categories a where a.isDeleted = :isDeleted")
    List<Categories> findAllNotDeleted(boolean isDeleted);

}
