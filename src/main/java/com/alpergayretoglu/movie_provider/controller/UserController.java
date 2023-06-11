package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.request.auth.ResetPasswordRequest;
import com.alpergayretoglu.movie_provider.model.request.user.UserCreateRequest;
import com.alpergayretoglu.movie_provider.model.request.user.UserUpdateRequest;
import com.alpergayretoglu.movie_provider.model.response.InvoiceResponse;
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

    @ApiPageable
    @GetMapping
    public Page<UserResponse> listUsers(@ApiIgnore Pageable pageable) {
        return userService.listUsers(pageable).map(UserResponse::fromEntity);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        return UserResponse.fromEntity(userService.getUser(userId));
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return UserResponse.fromEntity(userService.createUser(userCreateRequest));
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return UserResponse.fromEntity(userService.updateUser(userId, userUpdateRequest));
    }

    @DeleteMapping("/{userId}")
    public UserResponse deleteUser(@PathVariable String userId) {
        return UserResponse.fromEntity(userService.deleteUser(userId));
    }

    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(authenticationService.getAuthenticatedUser(), resetPasswordRequest);
    }

    @PostMapping("/{userId}/subscribe/{subscriptionId}")
    public void subscribe(@PathVariable String userId, @PathVariable String subscriptionId) {
        userService.subscribe(userId, subscriptionId);
    }

    @ApiPageable
    @GetMapping("/{userId}/invoice")
    public Page<InvoiceResponse> listInvoices(@PathVariable String userId, @ApiIgnore Pageable pageable) {
        return userService.listInvoicesForUser(userId, pageable);
    }

}