package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.BusinessException;
import com.alpergayretoglu.movie_provider.exception.ErrorCode;
import com.alpergayretoglu.movie_provider.model.entity.User;
import com.alpergayretoglu.movie_provider.model.enums.UserRole;
import com.alpergayretoglu.movie_provider.model.request.user.CreateUserRequest;
import com.alpergayretoglu.movie_provider.model.request.user.UpdateUserRequest;
import com.alpergayretoglu.movie_provider.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));
    }

    public User createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new BusinessException(ErrorCode.account_already_exists, "Account already exists");
        }

        User newUser = CreateUserRequest.toEntity(createUserRequest);
        newUser.setPasswordHash(passwordEncoder.encode(createUserRequest.getPassword()));
        newUser.setUserRole(UserRole.GUEST);
        newUser.setVerified(false);

        return userRepository.save(newUser);
    }

    public User updateUser(String userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "User not found"));

        return userRepository.save(UpdateUserRequest.toEntity(user, updateUserRequest));
    }

    public User deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "User not found"));

        userRepository.delete(user);

        return user;
    }

}
