package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.Playlists;
import com.example.e_project_4_api.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlists,Integer>{
}
