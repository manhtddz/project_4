package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateCategory;
import com.example.e_project_4_api.dto.response.common_response.CategoryResponse;
import com.example.e_project_4_api.dto.response.display_response.AlbumDisplay;
import com.example.e_project_4_api.dto.response.mix_response.CategoryWithAlbumsResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Artists;
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
public class CategoryService {
    @Autowired
    CategoryRepository cateRepository;
    @Autowired
    CategoryAlbumRepository cateAlbumRepository;
    @Autowired
    private AlbumRepository albumRepository;

    public List<CategoryResponse> getAllCategories() {
        return cateRepository.findAllNotDeleted(false)
                .stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public List<CategoryWithAlbumsResponse> getAllCategoriesWithAlbums() {
        return cateRepository.findAllNotDeleted(false)
                .stream()
                .map(this::toCategoryWithAlbumsResponse)
                .collect(Collectors.toList());
    }


    public CategoryResponse findById(int id) {
        Optional<Categories> op = cateRepository.findByIdAndIsDeleted(id, false);
        if (op.isPresent()) {
            Categories subjects = op.get();
            return toCategoryResponse(subjects);
        } else {
            throw new NotFoundException("Can't find any category with id: " + id);
        }
    }

    public void deleteById(int id) {
        if (!cateRepository.existsById(id)) {
            throw new NotFoundException("Can't find any category with id: " + id);
        }
        cateRepository.deleteById(id);
    }

    public NewOrUpdateCategory addNewSubject(NewOrUpdateCategory request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<Categories> op = cateRepository.findByTitle(request.getTitle());
        if (op.isPresent()) {
            errors.add(Map.of("titleError", "Already exist song with title: " + request.getTitle()));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Date currentDate = new Date();
        Categories newSub = new Categories(request.getTitle(), request.getImage(), request.getDescription(), false, currentDate, currentDate);
        cateRepository.save(newSub);
        return request;
    }

    public NewOrUpdateCategory updateSubject(NewOrUpdateCategory request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<Categories> op = cateRepository.findByIdAndIsDeleted(request.getId(),false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any category with id: " + request.getId());
        }

        Optional<Categories> opTitle = cateRepository.findByTitle(request.getTitle());
        if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
            errors.add(Map.of("titleError", "Already exist song with title: " + request.getTitle()));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Date currentDate = new Date();
        Categories sub = op.get();
        sub.setTitle(request.getTitle());
        sub.setImage(request.getImage());
        sub.setDescription(request.getDescription());
        sub.setModifiedAt(currentDate);
        cateRepository.save(sub);
        return request;
    }

    private CategoryResponse toCategoryResponse(Categories sub) {
        CategoryResponse res = new CategoryResponse();
        res.setIsDeleted(sub.getIsDeleted());
        BeanUtils.copyProperties(sub, res);
        return res;
    }

    private CategoryWithAlbumsResponse toCategoryWithAlbumsResponse(Categories category) {
        List<CategoryAlbum> categoryAlbum = cateAlbumRepository.findAllByCategoryId(category.getId(), false);

        List<AlbumDisplay> albumsOfCategory = categoryAlbum.stream()
                .map(it -> it.getAlbumId())
                .map(this::toAlbumDisplay)
                .collect(Collectors.toList());

        CategoryWithAlbumsResponse res = new CategoryWithAlbumsResponse();
        BeanUtils.copyProperties(category, res);
        res.setAlbums(albumsOfCategory);
        return res;
    }

    public AlbumDisplay toAlbumDisplay(Albums album) {
        AlbumDisplay res = new AlbumDisplay();
        BeanUtils.copyProperties(album, res);
        res.setIsDeleted(album.getIsDeleted());
        res.setIsReleased(album.getIsReleased());
        res.setArtistName(album.getArtistId().getArtistName());
        res.setArtistImage(album.getArtistId().getImage());
        return res;
    }
}