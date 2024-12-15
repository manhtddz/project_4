package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Songs;
import com.example.e_project_4_api.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByUsernameAndIsDeleted(String username, boolean isDeleted);


//    Optional<Users> findByIdAndIsDeleted(Integer id, boolean isDeleted);
//
//    @Query("Select a from Users a where a.isDeleted = :isDeleted")
//    List<Users> findAllNotDeleted(boolean isDeleted);

}
