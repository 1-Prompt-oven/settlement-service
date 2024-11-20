package com.promptoven.settlementservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BankAccountModelDTO {

	private final String accountID;
	private final String bankName;
}
