package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.BusinessException;
import com.alpergayretoglu.movie_provider.exception.ErrorCode;
import com.alpergayretoglu.movie_provider.model.entity.User;
import com.alpergayretoglu.movie_provider.model.request.auth.EmailRecoveryRequest;
import com.alpergayretoglu.movie_provider.model.request.auth.EmailRequest;
import com.alpergayretoglu.movie_provider.model.request.auth.EmailVerificationRequest;
import com.alpergayretoglu.movie_provider.model.request.auth.ResetPasswordRequest;
import com.alpergayretoglu.movie_provider.model.request.auth.login.LoginRequest;
import com.alpergayretoglu.movie_provider.model.request.auth.register.RegisterRequest;
import com.alpergayretoglu.movie_provider.model.response.LoginResponse;
import com.alpergayretoglu.movie_provider.repository.UserRepository;
import com.alpergayretoglu.movie_provider.security.JwtService;
import com.alpergayretoglu.movie_provider.service.client.EmailClient;
import com.alpergayretoglu.movie_provider.util.DateUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailClient emailClient;

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_EXISTS, "Account already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setVerificationCode(generateRandomStringCode());
        user.setVerificationCodeExpirationDate(generateExpirationDate());
        user.setVerified(false);
        userRepository.save(user);

        emailClient.sendVerificationEmail(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = getUserWithException(loginRequest.getEmail());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH, "Wrong Password");
        }

        return LoginResponse.builder()
                .id(user.getId())
                .token(jwtService.createToken(user.getId()))
                .userRole(user.getUserRole())
                .build();
    }

    public Optional<User> getAuthenticatedUser() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            return Optional.empty();
        }
        return userRepository.findById(principal);
    }

    public void sendVerificationEmail(EmailRequest emailRequest) {
        User user = getUserWithException(emailRequest.getEmail());

        if (user.isVerified()) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "User is already verified");
        }

        user.setVerificationCode(generateRandomStringCode());
        user.setVerificationCodeExpirationDate(generateExpirationDate());
        userRepository.save(user);

        emailClient.sendVerificationEmail(user);
    }

    public void verify(EmailVerificationRequest emailVerificationRequest) {
        User user = getUserWithException(emailVerificationRequest.getEmail());

        if (user.getVerificationCodeExpirationDate().isBefore(DateUtil.now())) {
            throw new BusinessException(ErrorCode.CODE_EXPIRED, "Verification code has expired");
        }

        if (!user.getVerificationCode().equals(emailVerificationRequest.getVerificationCode())) {
            throw new BusinessException(ErrorCode.CODE_MISMATCH, "Wrong verification code");
        }

        user.setRecoveryCodeExpirationDate(null);
        user.setRecoveryCode(null);
        user.setVerified(true);
        user.setPasswordHash(passwordEncoder.encode(emailVerificationRequest.getPassword()));
        userRepository.save(user);
    }

    public void recovery(EmailRecoveryRequest emailRecoveryRequest) {
        User user = getUserWithException(emailRecoveryRequest.getEmail());

        if (user.getRecoveryCodeExpirationDate().isBefore(DateUtil.now())) {
            throw new BusinessException(ErrorCode.CODE_EXPIRED, "The code is expired!");
        }

        if (!user.getRecoveryCode().equals(emailRecoveryRequest.getRecoveryCode())) {
            throw new BusinessException(ErrorCode.CODE_MISMATCH, "The codes dont match!");
        }

        user.setRecoveryCode(null);
        user.setRecoveryCodeExpirationDate(null);
        user.setPasswordHash(passwordEncoder.encode(emailRecoveryRequest.getNewPassword()));

        userRepository.save(user);
    }

    public void sendRecoveryEmail(EmailRequest emailRequest) {
        User user = getUserWithException(emailRequest.getEmail());

        user.setRecoveryCode(generateRandomStringCode());
        user.setRecoveryCodeExpirationDate(generateExpirationDate());
        userRepository.save(user);

        emailClient.sendRecoveryEmail(user);
    }

    public void resetPassword(Optional<User> userOptional, ResetPasswordRequest resetPasswordRequest) {
        User user = userOptional
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_MISSING, "User not found"));

        if (!passwordEncoder.matches(resetPasswordRequest.getOldPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH, "Password mismatch.");
        }

        user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);
    }


    // COMMON METHODS

    private User getUserWithException(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_MISSING, "User not found"));
    }

    private String generateRandomStringCode() {
        return RandomStringUtils.randomAlphanumeric(24);
    }

    private ZonedDateTime generateExpirationDate() {
        return DateUtil.now().plusDays(1);
    }

}
