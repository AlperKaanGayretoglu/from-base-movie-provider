package com.alpergayretoglu.movie_provider.model.request.auth.login;

import com.alpergayretoglu.movie_provider.constants.ApplicationConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Email
    @NotBlank
    private String email;

    @Size(min = ApplicationConstants.PASSWORD_MIN_LENGTH, max = ApplicationConstants.PASSWORD_MAX_LENGTH)
    private String password;

}
