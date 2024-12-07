package com.promptoven.settlementservice.application.port.in.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LedgerAppendRequestDTO {

	private final String sellerUUID;
	private final String productName;
	private final Long price;
	private final LocalDateTime soldAt;
}
