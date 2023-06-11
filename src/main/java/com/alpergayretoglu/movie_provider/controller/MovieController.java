package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.request.movie.MovieCreateRequest;
import com.alpergayretoglu.movie_provider.model.request.movie.MovieUpdateRequest;
import com.alpergayretoglu.movie_provider.model.response.MovieResponse;
import com.alpergayretoglu.movie_provider.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @ApiPageable
    public Page<MovieResponse> listMovies(@ApiIgnore Pageable pageable) {
        return movieService.listMovies(pageable);
    }

    @GetMapping("/{movieId}")
    public MovieResponse getMovie(@PathVariable String movieId) {
        return movieService.getMovie(movieId);
    }

    @PostMapping
    public MovieResponse createMovie(@RequestBody MovieCreateRequest request) {
        return movieService.createMovie(request);
    }

    @PutMapping("/{movieId}")
    public MovieResponse updateMovie(@PathVariable String movieId, @RequestBody MovieUpdateRequest request) {
        return movieService.updateMovie(movieId, request);
    }

}