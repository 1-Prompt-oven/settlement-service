package com.promptoven.settlementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LedgerSettlement {

	private String settlementUUID;
	private String bank;
	private String bankAccount;
	private Long settled;
	private Long nationalTax;
	private Long regionalTax;
	private Long platform

}
