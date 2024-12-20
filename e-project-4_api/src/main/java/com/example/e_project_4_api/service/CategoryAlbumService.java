package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateCategoryAlbum;
import com.example.e_project_4_api.dto.response.common_response.CategoryAlbumResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Categories;
import com.example.e_project_4_api.models.CategoryAlbum;
import com.example.e_project_4_api.repositories.AlbumRepository;
import com.example.e_project_4_api.repositories.CategoryAlbumRepository;
import com.example.e_project_4_api.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryAlbumService {
    @Autowired
    private CategoryAlbumRepository cateAlbumRepo;
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private CategoryRepository cateRepo;


    public List<CategoryAlbumResponse> getAllSubjectAlbums() {
        return cateAlbumRepo.findAll()
                .stream()
                .map(this::toCategoryAlbumResponse)
                .collect(Collectors.toList());
    }


    public CategoryAlbumResponse findById(int id) {
        Optional<CategoryAlbum> op = cateAlbumRepo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any subject album with id: " + id);
        }
        return toCategoryAlbumResponse(op.get());
    }


    public boolean deleteById(int id) {
        Optional<CategoryAlbum> categoryAlbum = cateAlbumRepo.findById(id);
        if (categoryAlbum.isEmpty()) {
            throw new NotFoundException("Can't find any category_album with id: " + id);
        }
        CategoryAlbum existing = categoryAlbum.get();
        cateAlbumRepo.delete(existing);
        return true;
    }


    public NewOrUpdateCategoryAlbum addNewCategoryAlbum(NewOrUpdateCategoryAlbum request) {
        Optional<CategoryAlbum> existingCategoryAlbum = cateAlbumRepo.findByCategoryIdAndAlbumId(request.getCategoryId(), request.getAlbumId());
        if (existingCategoryAlbum.isPresent()) {
            throw new AlreadyExistedException("A CategoryAlbum already exists");
        }

        Optional<Albums> album = albumRepo.findById(request.getAlbumId());
        if (album.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + request.getAlbumId());
        }


        Optional<Categories> cate = cateRepo.findById(request.getCategoryId());
        if (cate.isEmpty()) {
            throw new NotFoundException("Can't find any category with id: " + request.getCategoryId());
        }

        CategoryAlbum newSubjectAlbum = new CategoryAlbum(
                album.get(),
                cate.get()
        );
        cateAlbumRepo.save(newSubjectAlbum);
        return request;
    }


    public NewOrUpdateCategoryAlbum updateCategoryAlbum(NewOrUpdateCategoryAlbum request) {

        Optional<CategoryAlbum> op = cateAlbumRepo.findById(request.getId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any category album with id: " + request.getId());
        }


        Optional<Albums> album = albumRepo.findById(request.getAlbumId());
        if (album.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + request.getAlbumId());
        }


        Optional<Categories> cate = cateRepo.findById(request.getCategoryId());
        if (cate.isEmpty()) {
            throw new NotFoundException("Can't find any category with id: " + request.getCategoryId());
        }

        CategoryAlbum categoryAlbum = op.get();
        categoryAlbum.setAlbumId(album.get());
        categoryAlbum.setCategoryId(cate.get());
        cateAlbumRepo.save(categoryAlbum);

        return request;
    }


    public CategoryAlbumResponse toCategoryAlbumResponse(CategoryAlbum cateAlbum) {
        CategoryAlbumResponse res = new CategoryAlbumResponse();
        BeanUtils.copyProperties(cateAlbum, res);
        res.setAlbumId(cateAlbum.getAlbumId().getId());
        res.setCategoryId(cateAlbum.getCategoryId().getId());
        return res;
    }


}
