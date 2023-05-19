package com.alpergayretoglu.movie_provider.repository;

import com.alpergayretoglu.movie_provider.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
}