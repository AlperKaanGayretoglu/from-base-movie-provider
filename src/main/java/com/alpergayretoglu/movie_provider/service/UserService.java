package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.BadRequestException;
import com.alpergayretoglu.movie_provider.exception.BusinessException;
import com.alpergayretoglu.movie_provider.exception.ErrorCode;
import com.alpergayretoglu.movie_provider.model.entity.*;
import com.alpergayretoglu.movie_provider.model.enums.UserRole;
import com.alpergayretoglu.movie_provider.model.request.user.CreateUserRequest;
import com.alpergayretoglu.movie_provider.model.request.user.UpdateUserRequest;
import com.alpergayretoglu.movie_provider.model.response.InvoiceResponse;
import com.alpergayretoglu.movie_provider.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

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

    // TODO: DEFAULT METHODS
    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_MISSING, "User not found with id: " + userId));
    }

    public User createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_EXISTS, "Account already exists");
        }

        User newUser = CreateUserRequest.toEntity(createUserRequest);
        newUser.setPasswordHash(passwordEncoder.encode(createUserRequest.getPassword()));
        newUser.setUserRole(UserRole.GUEST);
        newUser.setVerified(false);

        return userRepository.save(newUser);
    }

    public User updateUser(String userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_MISSING, "User not found with id: " + userId));

        return userRepository.save(UpdateUserRequest.toEntity(user, updateUserRequest));
    }

    public User deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_MISSING, "User not found with id: " + userId));

        userRepository.delete(user);

        return user;
    }

    public ContractRecord subscribe(String userId, String subscriptionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_MISSING, "User not found with id: " + userId));
        Subscription subscription = subscriptionService.findById(subscriptionId);

        // check if user is verified
        if (!user.isVerified()) throw new BadRequestException("only verified users can subscribe");

        // check if user has already a subscription (allow only upgrading)
        ContractRecord contractRecord = user.getContractRecord();

        if (contractRecord != null) {
            if (contractRecord.getDuration() >= subscription.getDuration()) {
                throw new BadRequestException("you can only upgrade your subscription");
            }
        }

        // when a guest user buys a subscription, assign a member role
        if (user.getUserRole() == UserRole.GUEST) user.setUserRole(UserRole.MEMBER);

        if (contractRecord == null) return contractRecordService.addContract(user, subscription);
        else return contractRecordService.updateContract(contractRecord, subscription);
    }


    public Page<InvoiceResponse> listInvoicesForUser(String userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_MISSING, "User not found with id: " + userId));

        return contractRecordService.listInvoicesForUser(user, pageable);
    }

    // Interests : Follow Categories and Favorite Movies
    public User favoriteMovie(String userId, String movieId) {
        return addOrRemoveMovieFromUserFavoriteMovies(userId, movieId, true);
    }

    public User unfavoriteMovie(String userId, String movieId) {
        return addOrRemoveMovieFromUserFavoriteMovies(userId, movieId, false);
    }

    public User followCategory(String userId, String categoryId) {
        return followHelper(userId, categoryId, true);
    }

    public User unfollowCategory(String userId, String categoryId) {
        return followHelper(userId, categoryId, false);
    }

    /**
     * Helper method to avoid duplicate codes.
     * boolean isAddition field checks if it is a favorite or unfavorite method
     */
    private User addOrRemoveMovieFromUserFavoriteMovies(String userId, String movieId, boolean isAddition) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_MISSING, "User not found with id: " + userId));
        Movie movie = movieService.findMovieById(movieId);
        Set<Movie> movies = user.getFavoriteMovies();

        if (isAddition) {
            movies.add(movie);
        } else {
            movies.remove(movie);
        }

        userRepository.save(user);
        return user;
    }

    /**
     * Helper method to avoid duplicate codes. <br>
     * (TODO still needs a refactor of duplicate codes with addOrRemoveMovieFromUserFavoriteMovies method) <br>
     * To refactor, maybe use ICrudService interface? and give the method of class and its service. <br>
     * boolean isFollow field checks if it is a follow or unfollow request
     */
    private User followHelper(String userId, String categoryId, boolean isFollow) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_MISSING, "User not found with id: " + userId));
        Category category = categoryService.findCategoryById(categoryId);

        List<Category> categories = user.getFollowedCategories();

        if (isFollow && !categories.contains(category)) categories.add(category);
        else categories.remove(category);
        userRepository.save(user);
        return user;
    }

}
