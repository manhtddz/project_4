package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects,Integer>{
}
