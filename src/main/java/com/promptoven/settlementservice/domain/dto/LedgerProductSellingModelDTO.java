package com.promptoven.settlementservice.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LedgerProductSellingModelDTO {

	private final String sellerUUID;
	private final String productName;
	private final Long price;
	private final LocalDateTime soldAt;
}
