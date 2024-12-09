package com.promptoven.settlementservice.adaptor.web.controller.vo.out;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AdminSettlementHistoryResponseVO {

	private final LocalDate targetDate;
	private final Long accumulatedSold;
	private final Long accumulatedEarned;
	private final Long accumulatedSettledForSellerTax;
	private final Long accumulatedSettledForAdminTax;
}
