package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.LoginRequest;
import com.example.e_project_4_api.dto.request.NewOrUpdateUser;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Artists;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.ArtistRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import com.example.e_project_4_api.utilities.EmailValidator;
import com.example.e_project_4_api.utilities.PasswordValidator;
import com.example.e_project_4_api.utilities.PhoneNumberValidator;
import com.example.e_project_4_api.utilities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private ArtistRepository artistRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(NewOrUpdateUser request) {
        List<String> errors = new ArrayList<>();
        Users newUser = new Users();

        if (request.getUsername().isEmpty() || (request.getUsername() == null)) {
            //check null
            errors.add("Username is required");
        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Users> op = repo.findByUsername(request.getUsername());
            if (op.isPresent()) {
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
        if (request.getRole().isEmpty() || (request.getRole() == null)) {
            errors.add("Role is required");
        } else {
            if (!Objects.equals(request.getRole(), Role.ROLE_USER.toString())
                    && !Objects.equals(request.getRole(), Role.ROLE_ADMIN.toString())
                    && !Objects.equals(request.getRole(), Role.ROLE_ARTIST.toString())) {
                errors.add("Role is not valid");
            }
            if (request.getRole().equals(Role.ROLE_ARTIST.toString())) {
                Optional<Artists> artist = artistRepo.findById(request.getArtistId());
                if (artist.isPresent()) {
                    newUser.setArtistId(artist.get());
                } else {
                    errors.add("Can't find any artist with id: " + request.getArtistId());
                }
            }
        }


        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        newUser.setUsername(request.getUsername());
        newUser.setPassword(encoder.encode(request.getPassword()));
        newUser.setFullName(request.getFullName());
        newUser.setAvatar(request.getAvatar());
        newUser.setPhone(request.getPhone());
        newUser.setEmail(request.getEmail());
        newUser.setRole(request.getRole());
        newUser.setBio(request.getBio());
        newUser.setDob(request.getDob());
        newUser.setIsDeleted(false);
        newUser.setCreatedAt(request.getCreatedAt());
        newUser.setModifiedAt(request.getModifiedAt());

        repo.save(newUser);
        return newUser;
    }

    public String verify(LoginRequest request) {
        List<String> errors = new ArrayList<>();
        if (request.getUsername().isEmpty() || (request.getUsername() == null)) {
            //check null
            errors.add("Username is required");
        }
        if (request.getPassword().isEmpty() || (request.getPassword() == null)) {
            //check null
            errors.add("Password is required");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        } else {
            return "Wrong password or username";
        }
    }
}
