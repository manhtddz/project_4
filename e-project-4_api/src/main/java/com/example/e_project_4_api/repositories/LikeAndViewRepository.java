package com.example.e_project_4_api.repositories;

import com.example.e_project_4_api.models.CategoryAlbum;
import com.example.e_project_4_api.models.LikeAndViewInMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeAndViewRepository extends JpaRepository<LikeAndViewInMonth, Integer> {
    @Query("SELECT SUM(o.listenAmount) FROM LikeAndViewInMonth o WHERE o.monthId.id = :monthId")
    Optional<Integer> findTotalListenAmount(@Param("monthId") Integer monthId);

    @Query("SELECT SUM(o.likeAmount) FROM LikeAndViewInMonth o WHERE o.monthId.id = :monthId")
    Optional<Integer> findTotalLikeAmount(@Param("monthId") Integer monthId);

    @Query("SELECT o FROM LikeAndViewInMonth o WHERE o.listenAmount = (SELECT MAX(o2.listenAmount) FROM LikeAndViewInMonth o2) AND o.monthId.id = :monthId ORDER BY o.listenAmount DESC")
    List<LikeAndViewInMonth> findSongWithMaxListenAmount(@Param("monthId") Integer monthId, Pageable pageable);

    @Query("SELECT o FROM LikeAndViewInMonth o WHERE o.likeAmount = (SELECT MAX(o2.likeAmount) FROM LikeAndViewInMonth o2) AND o.monthId.id = :monthId ORDER BY o.likeAmount DESC")
    List<LikeAndViewInMonth> findSongWithMaxLikeAmount(@Param("monthId") Integer monthId, Pageable pageable);

    @Query("SELECT o FROM LikeAndViewInMonth o WHERE o.monthId.id = :monthId ORDER BY o.listenAmount DESC")
    List<LikeAndViewInMonth> findSongsWithMaxListenAmount(@Param("monthId") Integer monthId, Pageable pageable);

    @Query("SELECT sa FROM LikeAndViewInMonth sa WHERE sa.monthId.id = :monthId AND sa.songId.id = :songId")
    Optional<LikeAndViewInMonth> findBySongIdAndMonthId(@Param("monthId") Integer monthId, @Param("songId") Integer songId);
}
