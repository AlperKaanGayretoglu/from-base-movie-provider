package com.alpergayretoglu.movie_provider.repository;

import com.alpergayretoglu.movie_provider.model.entity.ContractRecord;
import com.alpergayretoglu.movie_provider.model.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    public Page<Invoice> findAllByContractRecordIn(List<ContractRecord> contractRecords, Pageable pageable);
}
