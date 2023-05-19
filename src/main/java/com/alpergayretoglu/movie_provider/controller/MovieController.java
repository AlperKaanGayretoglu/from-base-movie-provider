package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.response.MovieResponse;
import com.alpergayretoglu.movie_provider.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieResponse> listMovies() {
        return movieService.listMovies();
    }
}