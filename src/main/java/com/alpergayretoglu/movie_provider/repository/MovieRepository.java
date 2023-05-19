package com.alpergayretoglu.movie_provider.repository;

import com.alpergayretoglu.movie_provider.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {

}