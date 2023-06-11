package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import com.alpergayretoglu.movie_provider.model.response.MovieResponse;
import com.alpergayretoglu.movie_provider.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Movie> listMovies() {
        return repository.findAll();
    }

    public Page<MovieResponse> listMovies(Pageable pageable) {
        return repository.findAll(pageable).map(MovieResponse::fromEntity);
    }

    public MovieResponse getMovie(String movieId) {
        return MovieResponse.fromEntity(findMovieById(movieId));
    }
}