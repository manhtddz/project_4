package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateCategory;
import com.example.e_project_4_api.dto.response.common_response.CategoryResponse;
import com.example.e_project_4_api.dto.response.display_response.AlbumDisplay;
import com.example.e_project_4_api.dto.response.mix_response.CategoryWithAlbumsResponse;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        return cateRepository.findAll()
                .stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public List<CategoryWithAlbumsResponse> getAllCategoriesWithAlbums() {
        return cateRepository.findAll()
                .stream()
                .map(this::toCategoryWithAlbumsResponse)
                .collect(Collectors.toList());
    }


    public CategoryResponse findById(int id) {
        Optional<Categories> op = cateRepository.findById(id);
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
        List<String> errors = new ArrayList<>();
        if (request.getTitle().isEmpty() || (request.getTitle() == null)) {
            //check null
            errors.add("Title is required");
        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Categories> op = cateRepository.findByTitle(request.getTitle());
            if (op.isPresent()) {
                errors.add("Already exist category with title: " + request.getTitle());
            }
        }
        if (request.getImage().isEmpty() || (request.getImage() == null)) {
            errors.add("ImageURL is required");
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
        Optional<Categories> op = cateRepository.findById(request.getId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any category with id: " + request.getId());
        }
        List<String> errors = new ArrayList<>();

        if (request.getTitle().isEmpty() || (request.getTitle() == null)) {
            //check null
            errors.add("Title is required");
        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Categories> opTitle = cateRepository.findByTitle(request.getTitle());
            if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
                errors.add("Already exist category with title: " + request.getTitle());
            }
        }
        //check null
        if (request.getImage().isEmpty() || (request.getImage() == null)) {
            errors.add("ImageURL is required");
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
        List<CategoryAlbum> categoryAlbum = cateAlbumRepository.findAllByCategoryId(category.getId());

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