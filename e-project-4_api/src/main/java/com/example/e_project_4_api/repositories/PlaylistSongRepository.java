package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong,Integer> {
}
