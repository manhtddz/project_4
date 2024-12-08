package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer>{
    Optional<Users> findByUsername(String username);

}
