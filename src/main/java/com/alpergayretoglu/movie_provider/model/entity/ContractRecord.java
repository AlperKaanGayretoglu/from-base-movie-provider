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

    private int duration; // TODO: In days ???

    private boolean isActive;

    // TODO: Do we need to specify with which table ???
    @OneToOne
    private User user; // TODO: ER says "String user_id" but idk ???

    // TODO: Figure out if this is necessary ???
    // private ZonedDateTime createdDate;

    @OneToMany
    private List<Invoice> invoices;

    public int getRemainingDuration() {
        int totalDuration = this.duration;
        int remainingDuration = ZonedDateTime.now().compareTo(this.getCreated()) < 0 ? totalDuration : totalDuration - (int) ZonedDateTime.now().compareTo(this.getCreated());
        return Math.max(remainingDuration, 0);
    }
}
