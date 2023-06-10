package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.Invoice;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse extends BaseResponse {

    private int fee;
    private ContractRecordResponse contractRecord;

    public static InvoiceResponse fromEntity(Invoice invoice) {
        InvoiceResponse response = InvoiceResponse.builder()
                .fee(invoice.getFee())
                .contractRecord(ContractRecordResponse.fromEntity(invoice.getContractRecord()))
                .build();

        return setCommonValuesFromEntity(response, invoice);
    }
}