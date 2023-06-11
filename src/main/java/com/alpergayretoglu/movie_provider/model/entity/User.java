package com.alpergayretoglu.movie_provider.model.entity;

import com.alpergayretoglu.movie_provider.model.enums.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole userRole = UserRole.GUEST;

    @Column(name = "is_verified")
    @Builder.Default
    private boolean verified = false;

    @Column(name = "verification_code")
    @Builder.Default
    private String verificationCode = UUID.randomUUID().toString();

    @Column(name = "verification_code_expiration_date")
    @Builder.Default
    private ZonedDateTime verificationCodeExpirationDate = ZonedDateTime.now().plusDays(1);

    @Column(name = "recovery_code")
    private String recoveryCode;

    @Column(name = "recovery_code_expiration_date")
    private ZonedDateTime recoveryCodeExpirationDate;

    @ManyToOne
    private Subscription subscription;

    @ManyToMany
    @JoinTable(name = "users_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @Builder.Default
    private Set<Movie> favoriteMovies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "users_categories",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    @Builder.Default
    private Set<Category> followedCategories = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private ContractRecord contractRecord;

    public void addFavoriteMovie(Movie movie) {
        favoriteMovies.add(movie);
    }

    public void removeFavoriteMovie(Movie movie) {
        favoriteMovies.remove(movie);
    }

    public void addFollowedCategory(Category category) {
        followedCategories.add(category);
    }

    public void removeFollowedCategory(Category category) {
        followedCategories.remove(category);
    }

    // security layer:
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
