package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Subscription;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse extends BaseResponse {

    private String name;
    private int monthlyFee;
    private int duration;
    private boolean isActive;

    public static SubscriptionResponse fromEntity(Subscription subscription) {
        SubscriptionResponse response = SubscriptionResponse.builder()
                .name(subscription.getName())
                .monthlyFee(subscription.getMonthlyFee())
                .duration(subscription.getDuration())
                .isActive(subscription.isActive())
                .build();

        return setCommonValuesFromEntity(response, subscription);
    }
}