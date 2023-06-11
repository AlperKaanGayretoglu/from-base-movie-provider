package com.alpergayretoglu.movie_provider.model.request.subscription;

import com.alpergayretoglu.movie_provider.model.entity.Subscription;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionUpdateRequest {

    private String name;
    private int monthlyFee;
    private int duration;
    private boolean isActive;

    public static Subscription updateWith(Subscription subscription, SubscriptionUpdateRequest request) {
        subscription.setName(request.getName());
        subscription.setMonthlyFee(request.getMonthlyFee());
        subscription.setDuration(request.getDuration());
        subscription.setActive(request.isActive());
        return subscription;
    }

}
