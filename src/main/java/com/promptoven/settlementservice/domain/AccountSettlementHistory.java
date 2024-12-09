package com.promptoven.settlementservice.domain;

import java.time.LocalDate;

import com.promptoven.settlementservice.domain.dto.AccountSettlementModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AccountSettlementHistory {

	private LocalDate recordedAt;
	private String sellerUUID;
	private Long accumulatedSold;
	private Long accumulatedEarned;
	private Long accumulatedSettled;
	private Long thisYearlySold;
	private Long thisYearNationalTax;
	private Long thisYearLocalTax;
	private Long thisYearPlatformCharge;

	public static AccountSettlementHistory create(AccountSettlementModelDTO accountSettlementModelDTO) {
		return AccountSettlementHistory.builder()
			.recordedAt(LocalDate.now())
			.sellerUUID(accountSettlementModelDTO.getSellerUUID())
			.accumulatedSold(accountSettlementModelDTO.getAccumulatedSold())
			.accumulatedEarned(accountSettlementModelDTO.getAccumulatedEarned())
			.accumulatedSettled(accountSettlementModelDTO.getAccumulatedSettled())
			.thisYearlySold(accountSettlementModelDTO.getThisYearlyEarned())
			.build();
	}
}
