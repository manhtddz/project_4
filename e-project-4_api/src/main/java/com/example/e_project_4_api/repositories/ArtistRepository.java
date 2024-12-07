package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artists,Integer>{
}
