package com.alpergayretoglu.movie_provider.model.request.subscription;

import com.alpergayretoglu.movie_provider.model.entity.Subscription;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionCreateRequest {

    private String name;
    private int monthlyFee;
    private int duration;
    private boolean isActive;

    public static Subscription toEntity(SubscriptionCreateRequest request) {
        return Subscription.builder()
                .name(request.getName())
                .monthlyFee(request.getMonthlyFee())
                .duration(request.getDuration())
                .isActive(request.isActive())
                .build();
    }

}
