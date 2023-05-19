package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.model.entity.Payment;
import com.alpergayretoglu.movie_provider.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    public void addPayment(Payment payment) {
        repository.save(payment);
    }
}