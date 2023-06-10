package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Category;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import com.alpergayretoglu.movie_provider.model.entity.User;
import com.alpergayretoglu.movie_provider.model.enums.UserRole;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends BaseResponse {

    private String name;
    private String surname;
    private String email;
    private UserRole userRole;

    private boolean verified;

    // TODO: Implement these
    // private List<ContractRecord> subscriptionRecords;

    private List<String> followedCategories;
    private List<String> favoriteMovies;

    public static UserResponse fromEntity(User user) {
        UserResponse response = UserResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .verified(user.isVerified())
                .followedCategories(user.getFollowedCategories().stream().map(Category::getName).collect(Collectors.toList()))
                .favoriteMovies(user.getFavoriteMovies().stream().map(Movie::getTitle).collect(Collectors.toList()))
                .build();

        return setCommonValuesFromEntity(response, user);
    }

}
