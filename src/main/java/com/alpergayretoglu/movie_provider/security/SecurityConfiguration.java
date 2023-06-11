package com.alpergayretoglu.movie_provider.security;

import com.alpergayretoglu.movie_provider.model.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SelfFilter selfFilter;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, SelfFilter selfFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.selfFilter = selfFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()

                // PUBLIC
                .antMatchers("/", "/v2/api-docs/**", "/swagger.json", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/csrf").permitAll()
                .antMatchers("/auth/**").permitAll()

                .antMatchers(HttpMethod.GET,
                        "/subscription", "/subscription/**",

                        "/category", "/category/**",

                        "/movie", "/movie/**"
                ).permitAll()

                // ONLY SELF
                .antMatchers(HttpMethod.GET,
                        "/user/**/movie", "/user/**/movie/**",
                        "/user/**/category", "/user/**/category/**"
                ).hasAnyAuthority(SecurityConstants.SELF)

                .antMatchers(HttpMethod.POST,
                        "/user/**/subscribe/**",

                        "/user/**/movie/**/favorite", "/user/**/movie/**/unfavorite",
                        "/user/**/category/**/follow", "/user/**/category/**/unfollow",

                        "/invoice/**/pay"
                ).hasAnyAuthority(SecurityConstants.SELF)

                // ADMIN OR SELF
                .antMatchers(HttpMethod.GET,
                        "/user/**",

                        "/user/**/invoice"
                ).hasAnyAuthority(UserRole.ADMIN.name(), SecurityConstants.SELF)

                .antMatchers(HttpMethod.PUT,
                        "/user/**"
                ).hasAnyAuthority(UserRole.ADMIN.name(), SecurityConstants.SELF)


                // ADMIN ONLY
                .antMatchers(HttpMethod.GET,
                        "/user",

                        "/invoice", "/invoice/**"
                ).hasAnyAuthority(UserRole.ADMIN.name(), SecurityConstants.SELF)

                .antMatchers(HttpMethod.POST,
                        "/subscription",

                        "/category/**",

                        "/movie"
                ).hasAuthority(UserRole.ADMIN.name())

                .antMatchers(HttpMethod.PUT,
                        "/subscription/**",

                        "/category/**",

                        "/movie/**"
                ).hasAuthority(UserRole.ADMIN.name())

                .antMatchers(HttpMethod.DELETE,
                        "/subscription/**",

                        "/category/**",

                        "/movie/**"
                ).hasAuthority(UserRole.ADMIN.name())

                // OTHER
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(selfFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, SelfFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // TODO: Find out if this is necessary or not
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}