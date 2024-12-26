package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateLikeAndViewInMonth;
import com.example.e_project_4_api.dto.response.mix_response.SongWithLikeAndViewInMonth;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.models.CategoryAlbum;
import com.example.e_project_4_api.models.LikeAndViewInMonth;
import com.example.e_project_4_api.models.MonthOfYear;
import com.example.e_project_4_api.models.Songs;
import com.example.e_project_4_api.repositories.LikeAndViewRepository;
import com.example.e_project_4_api.repositories.MonthRepository;
import com.example.e_project_4_api.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeAndViewInMonthService {

    @Autowired
    private LikeAndViewRepository repo;
    @Autowired
    private MonthRepository mRepo;
    @Autowired
    private SongRepository sRepo;

    public int totalListenAmountInMonth(int monthId) {
        return repo.findTotalListenAmount(monthId).orElse(0);
    }

    public int totalLikeAmountInMonth(int monthId) {
        return repo.findTotalLikeAmount(monthId).orElse(0);
    }

    public void increaseListenAmountOrCreateNew(NewOrUpdateLikeAndViewInMonth request) {
        LocalDate cuDate = LocalDate.now();

        Optional<LikeAndViewInMonth> existing = repo.findBySongIdAndMonthId(cuDate.getMonthValue(), request.getSongId());
        if (existing.isPresent()) {
            LikeAndViewInMonth existed = existing.get();
            existed.setListenAmount(existed.getListenAmount() + 1);
            repo.save(existed);
            return;
        }
        LikeAndViewInMonth newObj = new LikeAndViewInMonth();
        Songs song = sRepo.findByIdAndIsDeleted(request.getSongId(), false)
                .orElseThrow(() -> new NotFoundException("Song not found with id: " + request.getSongId()));
        MonthOfYear month = mRepo.findById(cuDate.getMonthValue())
                .orElseThrow(() -> new NotFoundException("Month not found"));
        newObj.setMonthId(month);
        newObj.setSongId(song);
        newObj.setListenAmount(1);
        newObj.setLikeAmount(0);
        repo.save(newObj);
    }

    public void increaseLikeAmountOrCreateNew(NewOrUpdateLikeAndViewInMonth request) {
        LocalDate cuDate = LocalDate.now();

        Optional<LikeAndViewInMonth> existing = repo.findBySongIdAndMonthId(cuDate.getMonthValue(), request.getSongId());
        if (existing.isPresent()) {
            LikeAndViewInMonth existed = existing.get();
            existed.setLikeAmount(existed.getLikeAmount() + 1);
            repo.save(existed);
            return;
        }
        LikeAndViewInMonth newObj = new LikeAndViewInMonth();
        Songs song = sRepo.findByIdAndIsDeleted(request.getSongId(), false)
                .orElseThrow(() -> new NotFoundException("Song not found with id: " + request.getSongId()));
        MonthOfYear month = mRepo.findById(cuDate.getMonthValue())
                .orElseThrow(() -> new NotFoundException("Month not found"));
        newObj.setMonthId(month);
        newObj.setSongId(song);
        newObj.setListenAmount(0);
        newObj.setLikeAmount(1);
        repo.save(newObj);
    }
}
