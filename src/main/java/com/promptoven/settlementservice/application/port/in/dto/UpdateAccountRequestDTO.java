package com.promptoven.settlementservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UpdateAccountRequestDTO {

	private final String profileID;
	private final String accountID;
	private final String bankName;

}
