package com.promptoven.settlementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AccountSettlement {

	private String sellerUUID;
	private Long accumulatedSold;
	private Long accumulatedEarned;
	private Long accumulatedSettled;
	private Long thisYearlyEarned;
}
