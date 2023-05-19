package com.alpergayretoglu.movie_provider.model.request.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PaymentRequest {
    @NotNull
    private int amount;

    @NotBlank
    private String senderCard;

    @NotBlank
    private String receiverCard;

}