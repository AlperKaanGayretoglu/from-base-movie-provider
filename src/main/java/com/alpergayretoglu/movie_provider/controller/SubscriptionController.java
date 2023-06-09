package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.request.subscription.SubscriptionCreateRequest;
import com.alpergayretoglu.movie_provider.model.request.subscription.SubscriptionUpdateRequest;
import com.alpergayretoglu.movie_provider.model.response.SubscriptionResponse;
import com.alpergayretoglu.movie_provider.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @ApiPageable
    @GetMapping
    public Page<SubscriptionResponse> listSubscriptions(@ApiIgnore Pageable pageable) {
        return subscriptionService.listSubscriptions(pageable).map(SubscriptionResponse::fromEntity);
    }

    @GetMapping("{subId}")
    public SubscriptionResponse getSubscription(@PathVariable String subId) {
        return subscriptionService.getSubscription(subId);
    }

    @PostMapping
    public SubscriptionResponse createSubscription(@RequestBody SubscriptionCreateRequest request) {
        return SubscriptionResponse.fromEntity(subscriptionService.createSubscription(request));
    }

    @PutMapping("{subId}")
    public SubscriptionResponse updateSubscription(@PathVariable String subId, @RequestBody SubscriptionUpdateRequest request) {
        return SubscriptionResponse.fromEntity(subscriptionService.updateSubscription(subId, request));
    }

    @DeleteMapping("{subId}")
    public void deleteSubscription(@PathVariable String subId) {
        subscriptionService.deactivateSubscription(subId);
    }

}