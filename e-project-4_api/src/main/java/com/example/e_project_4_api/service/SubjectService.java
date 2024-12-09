package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateSubject;
import com.example.e_project_4_api.dto.response.SubjectResponse;
import com.example.e_project_4_api.ex.AlreadyExistedException;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.models.Subjects;
import com.example.e_project_4_api.repositories.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::toSubjectResponse)
                .collect(Collectors.toList());
    }

    public SubjectResponse findById(int id) {
        Optional<Subjects> op = subjectRepository.findById(id);
        if (op.isPresent()) {
            Subjects subjects = op.get();
            return toSubjectResponse(subjects);
        } else {
            throw new NotFoundException("Can't find any subjects with id: " + id);
        }
    }

    public void deleteById(int id) {
        if (!subjectRepository.existsById(id)) {
            throw new NotFoundException("Can't find any subjects with id: " + id);
        }
        subjectRepository.deleteById(id);
    }

    public NewOrUpdateSubject addNewSubject(NewOrUpdateSubject request) {
        Optional<Subjects> existingSubject = subjectRepository.findById(request.getId());
        if (existingSubject.isPresent()) {
            throw new AlreadyExistedException("A subject with this title already exists");
        }

        Date currentDate = new Date();
        Subjects newSub = new Subjects(request.getTitle(), request.getImage(), request.getDescription(), false, currentDate, currentDate);
        subjectRepository.save(newSub);
        return request;
    }

    public NewOrUpdateSubject updateSubject(NewOrUpdateSubject request) {
        Optional<Subjects> op = subjectRepository.findById(request.getId());
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any subjects with id: " + request.getId());
        }
        Date currentDate = new Date();
        Subjects sub = op.get();
        sub.setTitle(request.getTitle());
        sub.setImage(request.getImage());
        sub.setDescription(request.getDescription());
        sub.setModifiedAt(currentDate);
        subjectRepository.save(sub);
        return request;
    }

    private SubjectResponse toSubjectResponse(Subjects sub) {
        SubjectResponse res = new SubjectResponse();
//        res.setDeleted(sub.getIsDeleted());
        res.setIsDeleted(sub.getIsDeleted());
        BeanUtils.copyProperties(sub, res);
        return res;
    }
}
