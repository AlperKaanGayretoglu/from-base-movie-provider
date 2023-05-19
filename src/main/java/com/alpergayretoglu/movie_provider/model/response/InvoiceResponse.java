package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Invoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {
    private String id;
    private int fee;
    private ContractRecordResponse contractRecord;

    public static InvoiceResponse fromEntity(Invoice invoice) {
        return InvoiceResponse.builder()
                .id(invoice.getId())
                .fee(invoice.getFee())
                .contractRecord(ContractRecordResponse.fromEntity(invoice.getContractRecord()))
                .build();
    }
}