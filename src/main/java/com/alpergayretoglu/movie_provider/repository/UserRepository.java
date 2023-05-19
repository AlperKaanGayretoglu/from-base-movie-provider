package com.alpergayretoglu.movie_provider.repository;

import com.alpergayretoglu.movie_provider.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByEmail(String email);

    boolean existsByEmail(String email);

}
