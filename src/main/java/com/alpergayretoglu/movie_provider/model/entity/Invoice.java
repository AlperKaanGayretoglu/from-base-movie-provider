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

    // TODO: Do we need to specify with which table ???
    @ManyToOne
    private ContractRecord contractRecord; // TODO: ER says "String contract_record_id" but idk ???

}