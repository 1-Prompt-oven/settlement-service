package com.promptoven.settlementservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateTaxIDRequestDTO {

	private final String profileID;
	private final String taxID;
}
