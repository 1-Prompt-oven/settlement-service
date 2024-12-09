package com.promptoven.settlementservice.application.service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PlatformSettlementHistoryDTO {

	private final LocalDate recordedAt;
	private final Long accumulatedSold;
	private final Long accumulatedEarned;
	private final Long accumulatedSettledForSellerTax;
	private final Long accumulatedSettledForAdminTax;
	private final Long thisYearSettledForAdminTax;
	private final Long thisYearSettledForSellerTax;
	private final Long thisYearEarned;
}
