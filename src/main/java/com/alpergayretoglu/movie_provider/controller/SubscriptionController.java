package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.entity.Subscription;
import com.alpergayretoglu.movie_provider.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<Subscription> listSubscriptions() {
        return subscriptionService.listSubscriptions();
    }
}