package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private String token;
    private UserRole userRole;

}
