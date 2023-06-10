package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.Subscription;
import com.alpergayretoglu.movie_provider.model.response.SubscriptionResponse;
import com.alpergayretoglu.movie_provider.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository repository;

    public List<Subscription> listSubscriptions() {
        return repository.findAll();
    }

    public Page<SubscriptionResponse> listSubscriptions(Pageable pageable) {
        return repository.findAll(pageable).map(SubscriptionResponse::fromEntity);
    }

    public Subscription findById(String id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}