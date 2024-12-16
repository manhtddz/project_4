package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.LoginRequest;
import com.example.e_project_4_api.dto.request.NewOrUpdateUser;
import com.example.e_project_4_api.dto.response.common_response.LoginResponse;
import com.example.e_project_4_api.dto.response.common_response.UserResponse;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.Users;
import com.example.e_project_4_api.repositories.ArtistRepository;
import com.example.e_project_4_api.repositories.UserRepository;
import com.example.e_project_4_api.utilities.EmailValidator;
import com.example.e_project_4_api.utilities.PasswordValidator;
import com.example.e_project_4_api.utilities.PhoneNumberValidator;
import com.example.e_project_4_api.utilities.Role;
import org.springframework.beans.BeanUtils;
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

    public UserResponse register(NewOrUpdateUser request) {
        Users newUser = new Users();

        if (request.getUsername().isEmpty() || (request.getUsername() == null)) {
            //check null
            throw new ValidationException(Collections.singletonList("Username is required"));

        } else {
            // nếu ko null thì mới check unique title(do là album nên cần check trùng title)
            Optional<Users> op = repo.findByUsername(request.getUsername());
            if (op.isPresent()) {
                throw new ValidationException(Collections.singletonList("Already exist user with username: " + request.getUsername()));
            }
        }
        if (request.getFullName().isEmpty() || (request.getFullName() == null)) {
            throw new ValidationException(Collections.singletonList("Fullname is required"));

        }
        if (request.getAvatar().isEmpty() || (request.getAvatar() == null)) {
            throw new ValidationException(Collections.singletonList("Avatar is required"));

        }
        if (request.getPassword().isEmpty() || (request.getPassword() == null)) {
            throw new ValidationException(Collections.singletonList("Password is required"));

        } else {
            if (!PasswordValidator.isValidPassword(request.getPassword())) {
                throw new ValidationException(Collections
                        .singletonList("Password is not strong enough, at least 8 character with special character and number"));
            }
        }
        if (request.getPhone().isEmpty() || (request.getPhone() == null)) {
            throw new ValidationException(Collections.singletonList("Phone is required"));

        } else {
            if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhone())) {
                throw new ValidationException(Collections.singletonList("Phone number is not valid"));

            }
        }
        if (request.getEmail().isEmpty() || (request.getEmail() == null)) {
            throw new ValidationException(Collections.singletonList("Email is required"));

        } else {
            if (!EmailValidator.isValidEmail(request.getEmail())) {
                throw new ValidationException(Collections.singletonList("Email is not valid"));

            }
        }
        if (request.getRole().isEmpty() || (request.getRole() == null)) {
            throw new ValidationException(Collections.singletonList("Role is required"));

        } else {
            if (!Objects.equals(request.getRole(), Role.ROLE_USER.toString())
                    && !Objects.equals(request.getRole(), Role.ROLE_ADMIN.toString())
                    && !Objects.equals(request.getRole(), Role.ROLE_ARTIST.toString())) {
                throw new ValidationException(Collections.singletonList("Role is not valid"));

            }
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
        newUser.setIsActive(true);
        newUser.setCreatedAt(request.getCreatedAt());
        newUser.setModifiedAt(request.getModifiedAt());

        repo.save(newUser);
        return toUserResponse(newUser);
    }

    public LoginResponse verify(LoginRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            errors.add("Username is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.add("Password is required");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            Users user = repo.findByUsernameAndIsDeleted(request.getUsername(), false)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            if (user.getRole().equals(Role.ROLE_ARTIST.toString()) && !user.getIsActive()) {
                throw new ValidationException(Collections.singletonList("This user is not active"));
            }

            return new LoginResponse(jwtService.generateToken(request.getUsername()), toUserResponse(user));
        }

        return null;
    }

    public LoginResponse verifyForAdmin(LoginRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            errors.add("Username is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.add("Password is required");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            Users user = repo.findByUsernameAndIsDeleted(request.getUsername(), false)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            if(user.getRole().equals(Role.ROLE_USER.toString())){
                throw new ValidationException(Collections.singletonList("You don't have permission"));
            }
            if (user.getRole().equals(Role.ROLE_ARTIST.toString()) && !user.getIsActive()) {
                throw new ValidationException(Collections.singletonList("This user is not active"));
            }

            return new LoginResponse(jwtService.generateToken(request.getUsername()), toUserResponse(user));
        }

        return null;
    }


    public UserResponse toUserResponse(Users user) {
        UserResponse res = new UserResponse();
        BeanUtils.copyProperties(user, res);
        res.setIsDeleted(user.getIsDeleted());
        res.setIsActive(user.getIsActive());
        return res;
    }


}
