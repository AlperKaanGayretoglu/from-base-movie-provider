package com.alpergayretoglu.movie_provider.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends BaseEntity {

    private int fee;

    @ManyToOne
    private ContractRecord contractRecord;

    public void pay(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be less than or equal to 0");
        }
        if (amount > fee) {
            throw new IllegalArgumentException("Amount cannot be greater than fee");
        }
        fee -= amount;
    }

}