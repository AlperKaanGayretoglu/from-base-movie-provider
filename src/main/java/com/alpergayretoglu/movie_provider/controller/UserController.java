package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.request.auth.ResetPasswordRequest;
import com.alpergayretoglu.movie_provider.model.request.user.CreateUserRequest;
import com.alpergayretoglu.movie_provider.model.request.user.UpdateUserRequest;
import com.alpergayretoglu.movie_provider.model.response.UserResponse;
import com.alpergayretoglu.movie_provider.service.AuthenticationService;
import com.alpergayretoglu.movie_provider.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping
    @ApiPageable
    public Page<UserResponse> listUsers(@ApiIgnore Pageable pageable) {
        return userService.listUsers(pageable).map(UserResponse::fromEntity);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        return UserResponse.fromEntity(userService.getUser(userId));
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return UserResponse.fromEntity(userService.createUser(createUserRequest));
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return UserResponse.fromEntity(userService.updateUser(userId, updateUserRequest));
    }

    @DeleteMapping("/{userId}")
    public UserResponse deleteUser(@PathVariable String userId) {
        return UserResponse.fromEntity(userService.deleteUser(userId));
    }

    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(authenticationService.getAuthenticatedUser(), resetPasswordRequest);
    }

}