package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateGenres;
import com.example.e_project_4_api.dto.request.NewOrUpdateNews;
import com.example.e_project_4_api.dto.response.common_response.GenresResponse;
import com.example.e_project_4_api.dto.response.common_response.NewsResponse;
import com.example.e_project_4_api.dto.response.display_for_admin.NewsDisplayForAdmin;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Genres;
import com.example.e_project_4_api.models.News;
import com.example.e_project_4_api.repositories.GenresRepository;
import com.example.e_project_4_api.repositories.NewsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsService {
    @Autowired
    private NewsRepository repo;

    @Cacheable("newsDisplay")
    public List<NewsResponse> getAllNews() {
        return repo.findAll()
                .stream()
                .map(this::toNewsResponse)
                .collect(Collectors.toList());
    }

    @Cacheable("newsDisplayForAdmin")
    public List<NewsDisplayForAdmin> getAllNewsForAdmin() {
        return repo.findAll()
                .stream()
                .map(this::toNewsDisplayForAdmin)
                .collect(Collectors.toList());
    }


    public NewsResponse findById(int id) {
        Optional<News> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any news with id: " + id);
        }
        return toNewsResponse(op.get());
    }

    public NewsDisplayForAdmin findDisplayForAdminById(int id) {
        Optional<News> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any news with id: " + id);
        }
        return toNewsDisplayForAdmin(op.get());
    }

    @CacheEvict(value = {"newsDisplay", "newsDisplayForAdmin"})
    public boolean deleteById(int id) {
        Optional<News> news = repo.findById(id);
        if (news.isEmpty()) {
            throw new NotFoundException("Can't find any genre with id: " + id);
        }
        News existing = news.get();
        repo.delete(existing);
        return true;
    }

    @CacheEvict(value = {"newsDisplay", "newsDisplayForAdmin"})
    public NewOrUpdateNews addNew(NewOrUpdateNews request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<News> op = repo.findByTitle(request.getTitle());
        if (op.isPresent()) {
            errors.add(Map.of("titleError", "Already exist title"));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        News newNews = new News(
                request.getTitle(),
                request.getContent(),
                request.getImage(),
                false,
                new Date(),
                new Date()
        );

        repo.save(newNews);

        return request;
    }

    @CacheEvict(value = {"newsDisplay", "newsDisplayForAdmin"})
    public NewOrUpdateNews updateNews(NewOrUpdateNews request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<News> op = repo.findById(request.getId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any news with id: " + request.getId());

        }

        Optional<News> opTitle = repo.findByTitle(request.getTitle());
        if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
            errors.add(Map.of("titleError", "Already exist title"));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }


        News news = op.get();
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setImage(request.getImage());
        news.setModifiedAt(new Date());
        news.setIsActive(request.getIsActive());
        repo.save(news);

        return request;
    }


    public NewsResponse toNewsResponse(News news) {
        NewsResponse res = new NewsResponse();
        BeanUtils.copyProperties(news, res);
        return res;
    }

    public NewsDisplayForAdmin toNewsDisplayForAdmin(News news) {
        NewsDisplayForAdmin res = new NewsDisplayForAdmin();
        BeanUtils.copyProperties(news, res);
        return res;
    }
}
