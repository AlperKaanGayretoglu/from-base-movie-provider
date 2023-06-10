package com.alpergayretoglu.movie_provider.model.entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription extends BaseEntity {

    private String name;

    private int monthlyFee;

    private int duration;

    private boolean isActive;

}