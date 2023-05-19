package com.alpergayretoglu.movie_provider.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Payment extends BaseEntity {

    private int amount;

    private String senderCard;

    private String receiverCard;

    @ManyToOne
    private Invoice invoice;
}