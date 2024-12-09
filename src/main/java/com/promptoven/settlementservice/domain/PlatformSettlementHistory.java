package com.promptoven.settlementservice.domain;

import java.time.LocalDate;

import com.promptoven.settlementservice.domain.dto.PlatformSettlementHistoryModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PlatformSettlementHistory {

	private final LocalDate recordedAt;
	private final Long accumulatedSold;
	private final Long accumulatedEarned;
	private final Long accumulatedSettledForSellerTax;
	private final Long accumulatedSettledForAdminTax;
	private final Long thisYearSettledForAdminTax;
	private final Long thisYearSettledForSellerTax;
	private final Long thisYearEarned;


	public static PlatformSettlementHistory create(
		PlatformSettlementHistoryModelDTO platformSettlementHistoryModelDTO) {
		return PlatformSettlementHistory.builder()
			.recordedAt(LocalDate.now())
			.accumulatedSold(platformSettlementHistoryModelDTO.getAccumulatedSold())
			.accumulatedEarned(platformSettlementHistoryModelDTO.getAccumulatedEarned())
			.accumulatedSettledForSellerTax(platformSettlementHistoryModelDTO.getAccumulatedSettledForSellerTax())
			.accumulatedSettledForAdminTax(platformSettlementHistoryModelDTO.getAccumulatedSettledForAdminTax())
			.thisYearSettledForAdminTax(platformSettlementHistoryModelDTO.getThisYearSettledForAdminTax())
			.thisYearSettledForSellerTax(platformSettlementHistoryModelDTO.getThisYearSettledForSellerTax())
			.thisYearEarned(platformSettlementHistoryModelDTO.getThisYearEarned())
			.build();
	}
}
