package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.entity.User;
import com.alpergayretoglu.movie_provider.exception.BusinessException;
import com.alpergayretoglu.movie_provider.exception.ErrorCode;
import com.alpergayretoglu.movie_provider.model.request.user.CreateUserRequest;
import com.alpergayretoglu.movie_provider.model.request.user.UpdateUserRequest;
import com.alpergayretoglu.movie_provider.model.response.user.UserResponse;
import com.alpergayretoglu.movie_provider.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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

    public Page<UserResponse> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponse::fromEntity);
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));

        return UserResponse.fromEntity(user);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new BusinessException(ErrorCode.account_already_exists, "Account already exists");
        }

        User newUser = new User();
        newUser.setName(createUserRequest.getName());
        newUser.setSurname(createUserRequest.getSurname());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(12)));
        newUser.setUserRole(createUserRequest.getUserRole());
        newUser.setVerified(false);

        userRepository.save(newUser);

        return UserResponse.fromEntity(newUser);
    }

    public UserResponse updateUser(String userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "User not found"));

        user.setName(updateUserRequest.getName());
        user.setSurname(updateUserRequest.getSurname());
        user.setUserRole(updateUserRequest.getUserRole());

        userRepository.save(user);

        return UserResponse.fromEntity(user);
    }

    public UserResponse deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "User not found"));

        userRepository.delete(user);

        return UserResponse.fromEntity(user);
    }

}
