package com.alpergayretoglu.movie_provider.model.request.user;

import com.alpergayretoglu.movie_provider.constants.ApplicationConstants;
import com.alpergayretoglu.movie_provider.model.entity.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserCreateRequest {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Surname cannot be empty!")
    private String surname;

    @Email(message = "Invalid email address!")
    @NotBlank(message = "Mail address cannot be empty!")
    private String email;

    @NotBlank
    @Size(min = ApplicationConstants.PASSWORD_MIN_LENGTH, max = ApplicationConstants.PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull(message = "Email visibility cannot be empty!")
    @Builder.Default
    private Boolean emailVisible = false;

    @Builder.Default
    private String photoId = "";

    @NotBlank(message = "Description cannot be empty!")
    @Length(min = 20, max = 1500)
    @Builder.Default
    private String description = "";

    public static User toEntity(UserCreateRequest request) {
        return User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .build();
    }

}
