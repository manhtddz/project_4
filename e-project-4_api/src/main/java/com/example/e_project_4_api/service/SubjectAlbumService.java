package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateSubjectAlbum;
import com.example.e_project_4_api.dto.response.common_response.SubjectAlbumResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.PlaylistSong;
import com.example.e_project_4_api.models.SubjectAlbum;
import com.example.e_project_4_api.models.Albums;
import com.example.e_project_4_api.models.Subjects;
import com.example.e_project_4_api.repositories.SubjectAlbumRepository;
import com.example.e_project_4_api.repositories.AlbumRepository;
import com.example.e_project_4_api.repositories.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubjectAlbumService {
    @Autowired
    private SubjectAlbumRepository subjectAlbumRepo;
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private SubjectRepository subjectRepo;


    public List<SubjectAlbumResponse> getAllSubjectAlbums() {
        return subjectAlbumRepo.findAll()
                .stream()
                .map(this::toSubjectAlbumResponse)
                .collect(Collectors.toList());
    }


    public SubjectAlbumResponse findById(int id) {
        Optional<SubjectAlbum> op = subjectAlbumRepo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any subject album with id: " + id);
        }
        return toSubjectAlbumResponse(op.get());
    }


    public boolean deleteById(int id) {
        Optional<SubjectAlbum> subjectAlbum = subjectAlbumRepo.findById(id);
        if (subjectAlbum.isEmpty()) {
            throw new NotFoundException("Can't find any subject album with id: " + id);
        }
        SubjectAlbum existing = subjectAlbum.get();
        existing.setIsDeleted(true);
        subjectAlbumRepo.save(existing);
        return true;
    }


    public NewOrUpdateSubjectAlbum addNewSubjectAlbum(NewOrUpdateSubjectAlbum request) {
        List<String> errors = new ArrayList<>();
        Optional<SubjectAlbum> existingSubjectAlbum = subjectAlbumRepo.findBySubjectIdAndAlbumId(request.getSubjectId(),request.getAlbumId());
        if (existingSubjectAlbum.isPresent()) {
            throw new AlreadyExistedException("A SubjectAlbum already exists");
        }

        Optional<Albums> album = albumRepo.findById(request.getAlbumId());
        if (album.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + request.getAlbumId());
        }


        Optional<Subjects> subject = subjectRepo.findById(request.getSubjectId());
        if (subject.isEmpty()) {
            throw new NotFoundException("Can't find any subject with id: " + request.getSubjectId());
        }

        SubjectAlbum newSubjectAlbum = new SubjectAlbum(
                request.getIsDeleted(),
                new Date(),
                new Date(),
                album.get(),
                subject.get()
        );
        subjectAlbumRepo.save(newSubjectAlbum);
        return request;
    }


        public NewOrUpdateSubjectAlbum updateSubjectAlbum(NewOrUpdateSubjectAlbum request) {

        Optional<SubjectAlbum> op = subjectAlbumRepo.findById(request.getId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any subject album with id: " + request.getId());
        }


        Optional<Albums> album = albumRepo.findById(request.getAlbumId());
        if (album.isEmpty()) {
            throw new NotFoundException("Can't find any album with id: " + request.getAlbumId());
        }


        Optional<Subjects> subject = subjectRepo.findById(request.getSubjectId());
        if (subject.isEmpty()) {
            throw new NotFoundException("Can't find any subject with id: " + request.getSubjectId());
        }

        SubjectAlbum subjectAlbum = op.get();
        subjectAlbum.setAlbumId(album.get());
        subjectAlbum.setSubjectId(subject.get());
        subjectAlbum.setIsDeleted(request.getIsDeleted());
        subjectAlbum.setModifiedAt(new Date());
        subjectAlbumRepo.save(subjectAlbum);

        return request;
    }




    public SubjectAlbumResponse toSubjectAlbumResponse(SubjectAlbum subjectAlbum) {
        SubjectAlbumResponse res = new SubjectAlbumResponse();
        BeanUtils.copyProperties(subjectAlbum, res);
        res.setAlbumId(subjectAlbum.getAlbumId().getId());
        res.setSubjectId(subjectAlbum.getSubjectId().getId());
        res.setIsDeleted(subjectAlbum.getIsDeleted());
        return res;
    }


}
