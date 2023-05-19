package com.alpergayretoglu.movie_provider.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractRecord extends BaseEntity {

    private String name;

    private int monthlyFee;

    private int duration; // TODO: In days ???

    private boolean isActive;

    // TODO: Do we need to specify with which table ???
    @OneToOne
    private User user; // TODO: ER says "String user_id" but idk ???

    private ZonedDateTime createdDate;
}
