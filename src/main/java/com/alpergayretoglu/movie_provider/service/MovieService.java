package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import com.alpergayretoglu.movie_provider.model.response.MovieResponse;
import com.alpergayretoglu.movie_provider.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {

    private MovieRepository repository;

    public void addMovie(Movie movie) {
        repository.save(movie);
    }

    public Movie findMovieById(String movieId) {
        return repository.findById(movieId).orElseThrow(EntityNotFoundException::new);
    }

    public List<MovieResponse> listMovies() {
        return repository.findAll().stream()
                .map(MovieResponse::fromEntity).collect(Collectors.toList());
    }
}