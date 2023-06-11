package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.Subscription;
import com.alpergayretoglu.movie_provider.model.request.subscription.SubscriptionCreateRequest;
import com.alpergayretoglu.movie_provider.model.request.subscription.SubscriptionUpdateRequest;
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

    public SubscriptionResponse getSubscription(String subId) {
        return SubscriptionResponse.fromEntity(findById(subId));
    }

    public Subscription createSubscription(SubscriptionCreateRequest request) {
        return repository.save(SubscriptionCreateRequest.toEntity(request));
    }

    public Subscription updateSubscription(String subId, SubscriptionUpdateRequest request) {
        Subscription oldSubscription = findById(subId);
        return repository.save(SubscriptionUpdateRequest.updateWith(oldSubscription, request));
    }

    public void deactivateSubscription(String subId) {
        Subscription subscription = findById(subId);
        if (!subscription.isActive()) {
            throw new IllegalArgumentException("Subscription is already inactive");
        }
        subscription.setActive(false);
        repository.save(subscription);
    }
}