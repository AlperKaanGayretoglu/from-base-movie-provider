package com.alpergayretoglu.movie_provider.repository;

import com.alpergayretoglu.movie_provider.model.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData, String> {
}