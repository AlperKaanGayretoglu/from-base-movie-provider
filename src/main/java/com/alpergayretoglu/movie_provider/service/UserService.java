package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.BadRequestException;
import com.alpergayretoglu.movie_provider.exception.BusinessException;
import com.alpergayretoglu.movie_provider.exception.ErrorCode;
import com.alpergayretoglu.movie_provider.model.entity.*;
import com.alpergayretoglu.movie_provider.model.enums.UserRole;
import com.alpergayretoglu.movie_provider.model.request.user.UserCreateRequest;
import com.alpergayretoglu.movie_provider.model.request.user.UserUpdateRequest;
import com.alpergayretoglu.movie_provider.model.response.InvoiceResponse;
import com.alpergayretoglu.movie_provider.repository.UserRepository;
import com.alpergayretoglu.movie_provider.util.RestResponsePage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MovieService movieService;
    private final CategoryService categoryService;
    private final SubscriptionService subscriptionService;
    private final ContractRecordService contractRecordService;
    private final PasswordEncoder passwordEncoder;

    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_MISSING, "User not found with id: " + userId));
    }

    public User createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_EXISTS, "Account already exists");
        }

        User newUser = UserCreateRequest.toEntity(userCreateRequest);
        newUser.setPasswordHash(passwordEncoder.encode(userCreateRequest.getPassword()));
        newUser.setUserRole(UserRole.GUEST);
        newUser.setVerified(false);

        return userRepository.save(newUser);
    }

    public User updateUser(String userId, UserUpdateRequest userUpdateRequest) {
        User user = getUserById(userId);
        return userRepository.save(UserUpdateRequest.toEntity(user, userUpdateRequest));
    }

    public User deleteUser(String userId) {
        User user = getUserById(userId);
        userRepository.delete(user);

        return user;
    }

    public ContractRecord subscribe(String userId, String subscriptionId) {
        User user = getUserById(userId);
        Subscription subscription = subscriptionService.findById(subscriptionId);

        // check if user is verified
        if (!user.isVerified()) {
            throw new BadRequestException("Only verified users can subscribe");
        }

        // when a guest user buys a subscription, assign a member role
        if (user.getUserRole() == UserRole.GUEST) {
            user.setUserRole(UserRole.MEMBER);
        }

        ContractRecord oldContractRecord = user.getContractRecord();

        ContractRecord newContractRecord = (oldContractRecord == null) ?
                contractRecordService.addContract(user, subscription) :
                contractRecordService.updateContract(oldContractRecord, subscription);

        user.setContractRecord(newContractRecord);
        user.setSubscription(subscription);

        userRepository.save(user);

        return newContractRecord;
    }

    public Page<InvoiceResponse> listInvoicesForUser(String userId, Pageable pageable) {
        User user = getUserById(userId);
        return contractRecordService.listInvoicesForUser(user, pageable);
    }

    public Page<Movie> getFavoriteMovies(String userId, Pageable pageable) {
        User user = getUserById(userId);
        List<Movie> movies = new ArrayList<>(user.getFavoriteMovies());
        return new RestResponsePage<>(movies, pageable, movies.size());
    }

    public Movie getFavoriteMovie(String userId, String movieId) {
        User user = getUserById(userId);
        Movie movie = movieService.findMovieById(movieId);

        if (!user.getFavoriteMovies().contains(movie)) {
            throw new BusinessException(ErrorCode.RESOURCE_MISSING, "Movie not found in favorite movies");
        }

        return movie;
    }

    public Page<Category> getFollowedCategories(String userId, Pageable pageable) {
        User user = getUserById(userId);
        List<Category> categories = new ArrayList<>(user.getFollowedCategories());
        return new RestResponsePage<>(categories, pageable, categories.size());
    }

    public Category getFollowedCategory(String userId, String categoryId) {
        User user = getUserById(userId);
        Category category = categoryService.findCategoryById(categoryId);

        if (!user.getFollowedCategories().contains(category)) {
            throw new BusinessException(ErrorCode.RESOURCE_MISSING, "Category not found in followed categories");
        }

        return category;
    }

    public User favoriteMovie(String userId, String movieId) {
        User user = getUserById(userId);
        user.addFavoriteMovie(movieService.findMovieById(movieId));
        return userRepository.save(user);
    }

    public User unfavoriteMovie(String userId, String movieId) {
        User user = getUserById(userId);
        user.removeFavoriteMovie(movieService.findMovieById(movieId));
        return userRepository.save(user);
    }

    public User followCategory(String userId, String categoryId) {
        User user = getUserById(userId);
        user.addFollowedCategory(categoryService.findCategoryById(categoryId));
        return userRepository.save(user);
    }

    public User unfollowCategory(String userId, String categoryId) {
        User user = getUserById(userId);
        user.removeFollowedCategory(categoryService.findCategoryById(categoryId));
        return userRepository.save(user);
    }

}
