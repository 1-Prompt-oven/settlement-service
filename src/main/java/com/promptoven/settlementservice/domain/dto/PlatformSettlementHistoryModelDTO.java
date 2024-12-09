package com.promptoven.settlementservice.domain.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PlatformSettlementHistoryModelDTO {

	private final LocalDate targetDate;
	private final Long accumulatedSold;
	private final Long accumulatedEarned;
	private final Long accumulatedSettledForSellerTax;
	private final Long accumulatedSettledForAdminTax;
	private final Long thisYearSettledForAdminTax;
	private final Long thisYearSettledForSellerTax;
	private final Long thisYearEarned;
}
