package com.promptoven.settlementservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AccountSettlementModelDTO {

	private final String sellerUUID;
	private final Long accumulatedSold;
	private final Long accumulatedEarned;
	private final Long accumulatedSettled;
	private final Long thisYearlyEarned;

}
