package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.FavouriteSongs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteSongRepository extends JpaRepository<FavouriteSongs,Integer> {
}
