package com.alpergayretoglu.movie_provider.model.request.user;

import com.alpergayretoglu.movie_provider.model.entity.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateUserRequest {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Surname cannot be empty!")
    private String surname;

    @NotNull(message = "Email visibility cannot be empty!")
    @Builder.Default
    private Boolean emailVisible = false;

    @Builder.Default
    private String photoId = "";

    @NotBlank(message = "Description cannot be empty!")
    @Length(min = 20, max = 1500)
    @Builder.Default
    private String description = "";

    public static User toEntity(User user, UpdateUserRequest request) {
        return User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .build();
    }

}
