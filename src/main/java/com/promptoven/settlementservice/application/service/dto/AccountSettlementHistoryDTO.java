package com.promptoven.settlementservice.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class AccountSettlementHistoryDTO {

    private final LocalDate recordedAt;
    private final String sellerUUID;
    private final Long accumulatedSold;
    private final Long accumulatedEarned;
    private final Long accumulatedSettled;
    private final Long thisYearlyEarned;
}
