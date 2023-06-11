package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.response.SubscriptionResponse;
import com.alpergayretoglu.movie_provider.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @ApiPageable
    @GetMapping
    public Page<SubscriptionResponse> listSubscriptions(@ApiIgnore Pageable pageable) {
        return subscriptionService.listSubscriptions(pageable);
    }

    @GetMapping("{subId}")
    public SubscriptionResponse getSubscription(@PathVariable String subId) {
        return subscriptionService.getSubscription(subId);
    }

}