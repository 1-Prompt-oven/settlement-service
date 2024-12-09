package com.promptoven.settlementservice.application.service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SoldProductLedgerDTO {

	private final String sellerUUID;
	private final String productName;
	private final Long price;
	private final LocalDateTime soldAt;
	private final boolean settled;
	private final String orderID;
}
