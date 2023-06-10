package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.ContractRecord;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractRecordResponse extends BaseResponse {

    private String name;
    private int monthlyFee;
    private int duration;
    private int remainingDuration;
    private boolean isActive;
    private UserResponse user;

    // TODO: Implement this
    // @OneToMany
    // private List<Invoice> invoices;

    public static ContractRecordResponse fromEntity(ContractRecord contractRecord) {
        ContractRecordResponse response = ContractRecordResponse.builder()
                .name(contractRecord.getName())
                .monthlyFee(contractRecord.getMonthlyFee())
                .duration(contractRecord.getDuration())
                .remainingDuration(contractRecord.getRemainingDuration())
                .isActive(contractRecord.isActive())
                .build();

        return setCommonValuesFromEntity(response, contractRecord);
    }
}