package com.alpergayretoglu.movie_provider.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractRecord extends BaseEntity {

    private String name;

    private int monthlyFee;

    private int duration; // TODO: in invoices ??? and is it remaining or total ??? (I think it is total)

    private boolean isActive;

    @OneToOne
    private User user;

    @Builder.Default
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @OneToMany
    private List<Invoice> invoices;

    public int getRemainingDuration() {
        return duration - invoices.size();
    }
}
