package com.alpergayretoglu.movie_provider.security;

public class SecurityConstants {

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "MovieProvider";
    public static final String TOKEN_AUDIENCE = "MovieProvider";

    // Security Role
    public static final String SELF = "SELF";

    private SecurityConstants() {
    }

}
