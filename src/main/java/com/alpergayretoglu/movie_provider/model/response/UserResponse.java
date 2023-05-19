package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Category;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import com.alpergayretoglu.movie_provider.model.entity.User;
import com.alpergayretoglu.movie_provider.model.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UserResponse {

    private String id;
    private ZonedDateTime created;
    private ZonedDateTime updated;

    private String name;
    private String surname;
    private String email;
    private UserRole userRole;

    private boolean verified;

    private List<String> followedCategories;
    private List<String> favoriteMovies;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .updated(user.getUpdated())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .verified(user.isVerified())
                .followedCategories(user.getFollowedCategories().stream().map(Category::getName).collect(Collectors.toList()))
                .favoriteMovies(user.getFavoriteMovies().stream().map(Movie::getTitle).collect(Collectors.toList()))
                .build();
    }

}
