package com.alpergayretoglu.movie_provider.repository;

import com.alpergayretoglu.movie_provider.model.entity.ContractRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRecordRepository extends JpaRepository<ContractRecord, String> {
}
