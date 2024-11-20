package com.promptoven.settlementservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SettlementProfileModelDTO {
	private final String memberID;
	private final String settlementProfileID;
	// Tax ID must be Encrypted
	private final String taxID;
	private final String accountID;
	private final String bankName;
	private final String phone;
	private final String postcode;
	private final String address;
}
