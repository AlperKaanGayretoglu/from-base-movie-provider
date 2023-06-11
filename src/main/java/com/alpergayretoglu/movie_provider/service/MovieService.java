package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.Movie;
import com.alpergayretoglu.movie_provider.model.request.movie.MovieCreateRequest;
import com.alpergayretoglu.movie_provider.model.request.movie.MovieUpdateRequest;
import com.alpergayretoglu.movie_provider.model.response.MovieResponse;
import com.alpergayretoglu.movie_provider.repository.CategoryRepository;
import com.alpergayretoglu.movie_provider.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private MovieRepository movieRepository;
    private CategoryRepository categoryRepository;

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public Movie findMovieById(String movieId) {
        return movieRepository.findById(movieId).orElseThrow(EntityNotFoundException::new);
    }

    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    public Page<MovieResponse> listMovies(Pageable pageable) {
        return movieRepository.findAll(pageable).map(MovieResponse::fromEntity);
    }

    public MovieResponse getMovie(String movieId) {
        return MovieResponse.fromEntity(findMovieById(movieId));
    }

    public MovieResponse createMovie(MovieCreateRequest request) {
        return MovieResponse.fromEntity(movieRepository.save(MovieCreateRequest.toEntity(request, categoryRepository)));
    }

    public MovieResponse updateMovie(String movieId, MovieUpdateRequest request) {
        return MovieResponse.fromEntity(MovieUpdateRequest.updateWith(findMovieById(movieId), request, categoryRepository));
    }
}