package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateKeyword;
import com.example.e_project_4_api.dto.request.NewOrUpdateNews;
import com.example.e_project_4_api.dto.response.common_response.KeywordResponse;
import com.example.e_project_4_api.dto.response.common_response.NewsResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Keywords;
import com.example.e_project_4_api.models.News;
import com.example.e_project_4_api.repositories.KeywordRepository;
import com.example.e_project_4_api.repositories.NewsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KeywordService {
    @Autowired
    private KeywordRepository repo;


    public List<KeywordResponse> getAllKeywords() {
        return repo.findAll()
                .stream()
                .map(this::toKeywordResponse)
                .collect(Collectors.toList());
    }


    public KeywordResponse findById(int id) {
        Optional<Keywords> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any news with id: " + id);
        }
        return toKeywordResponse(op.get());
    }


    public boolean deleteById(int id) {
        Optional<Keywords> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any genre with id: " + id);
        }
        Keywords existing = op.get();
        repo.delete(existing);
        return true;
    }


    public NewOrUpdateKeyword addNew(NewOrUpdateKeyword request) {
        List<String> errors = new ArrayList<>();


        if (request.getContent() == null || request.getContent().isEmpty()) {
            errors.add("Content is required");
        } else {
            Optional<Keywords> op = repo.findByContent(request.getContent());
            if (op.isPresent()) {
                errors.add("Already exist keyword : " + request.getContent());
            }
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }


        Keywords newKeyword = new Keywords(
                request.getContent(),
                false,
                new Date(),
                new Date()
        );

        repo.save(newKeyword);

        return request;
    }


    public NewOrUpdateKeyword updateKeyword(NewOrUpdateKeyword request) {
        List<String> errors = new ArrayList<>();


        Optional<Keywords> op = repo.findById(request.getId());
        if (op.isEmpty()) {
            errors.add("Can't find any keywords with id: " + request.getId());
        }


        if (request.getContent() == null || request.getContent().isEmpty()) {
            errors.add("Content is required");
        } else {

            Optional<Keywords> opTitle = repo.findByContent(request.getContent());
            if (opTitle.isPresent() && opTitle.get().getContent() != op.get().getContent()) {
                errors.add("Already exist keyword: " + request.getContent());
            }
        }


        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }


        Keywords newKeyword = op.get();
        newKeyword.setContent(request.getContent());
        newKeyword.setModifiedAt(new Date());
        newKeyword.setIsActive(request.getIsActive());
        repo.save(newKeyword);

        return request;
    }


    public KeywordResponse toKeywordResponse(Keywords keyword) {
        KeywordResponse res = new KeywordResponse();
        BeanUtils.copyProperties(keyword, res);
        return res;
    }
}