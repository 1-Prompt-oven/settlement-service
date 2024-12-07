package com.promptoven.settlementservice.application.port.in.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SettlementHistoryRequestDTO {

	private final String sellerUUID;
	private final LocalDate beginDate;
	private final LocalDate endDate;
}
