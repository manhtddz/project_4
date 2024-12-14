package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateUser;
import com.example.e_project_4_api.dto.response.common_response.UserResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.ArtistRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import com.example.e_project_4_api.utilities.EmailValidator;
import com.example.e_project_4_api.utilities.PasswordValidator;
import com.example.e_project_4_api.utilities.PhoneNumberValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private ArtistRepository artistRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users updateUser(NewOrUpdateUser request) {
        List<String> errors = new ArrayList<>();
        Optional<Users> op = repo.findById(request.getId());
        Artists artist = null;
        if (op.isEmpty()) {
            errors.add("Can't find any user with id: " + request.getId());
        }
        if (request.getUsername().isEmpty() || (request.getUsername() == null)) {
            //check null
            errors.add("Username is required");
        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Users> opTitle = repo.findByUsername(request.getUsername());
            if (op.isPresent() && opTitle.get().getUsername() != op.get().getUsername()) {
                errors.add("Already exist user with username: " + request.getUsername());
            }
        }
        if (request.getFullName().isEmpty() || (request.getFullName() == null)) {
            errors.add("Fullname is required");
        }
        if (request.getAvatar().isEmpty() || (request.getAvatar() == null)) {
            errors.add("Avatar is required");
        }
        if (request.getPassword().isEmpty() || (request.getPassword() == null)) {
            errors.add("Password is required");
        } else {
            if (!PasswordValidator.isValidPassword(request.getPassword())) {
                errors.add("Password is not strong enough, at least 8 character with special character and number");
            }
        }
        if (request.getPhone().isEmpty() || (request.getPhone() == null)) {
            errors.add("Phone is required");
        } else {
            if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhone())) {
                errors.add("Phone number is not valid");
            }
        }
        if (request.getEmail().isEmpty() || (request.getEmail() == null)) {
            errors.add("Email is required");
        } else {
            if (!EmailValidator.isValidEmail(request.getEmail())) {
                errors.add("Email is not valid");
            }
        }


        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Users user = op.get();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setAvatar(request.getAvatar());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setBio(request.getBio());
        user.setDob(request.getDob());
        user.setIsDeleted(request.getIsDeleted());
        user.setIsActive(request.getIsActive());
        user.setModifiedAt(new Date());
        repo.save(user);
        return user;
    }

    public List<UserResponse> getAllUsers() {
        return repo.findAll()
                .stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }


    public UserResponse findById(int id) {
        Optional<Users> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any user with id: " + id);
        }
        return toUserResponse(op.get());
    }

    public boolean deleteById(int id) {
        Optional<Users> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any user with id: " + id);
        }
        Users existing = op.get();
        existing.setIsDeleted(true);
        repo.save(existing);
        return true;
    }

    public UserResponse toUserResponse(Users user) {
        UserResponse res = new UserResponse();
        BeanUtils.copyProperties(user, res);
        res.setIsDeleted(user.getIsDeleted());
        res.setIsActive(user.getIsActive());
        return res;
    }

}
