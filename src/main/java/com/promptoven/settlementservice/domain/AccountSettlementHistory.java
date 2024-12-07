package com.promptoven.settlementservice.domain;

import com.promptoven.settlementservice.domain.dto.AccountSettlementModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class AccountSettlementHistory {

	private LocalDate recordedAt;
	private String sellerUUID;
	private Long accumulatedSold;
	private Long accumulatedEarned;
	private Long accumulatedSettled;
	private Long thisYearlyEarned;

	public static AccountSettlementHistory create(AccountSettlementModelDTO accountSettlementModelDTO) {
		return AccountSettlementHistory.builder()
			.recordedAt(LocalDate.now())
			.sellerUUID(accountSettlementModelDTO.getSellerUUID())
			.accumulatedSold(accountSettlementModelDTO.getAccumulatedSold())
			.accumulatedEarned(accountSettlementModelDTO.getAccumulatedEarned())
			.accumulatedSettled(accountSettlementModelDTO.getAccumulatedSettled())
			.thisYearlyEarned(accountSettlementModelDTO.getThisYearlyEarned())
			.build();
	}
}
