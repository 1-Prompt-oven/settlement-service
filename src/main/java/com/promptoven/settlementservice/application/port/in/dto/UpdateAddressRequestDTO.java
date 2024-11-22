package com.promptoven.settlementservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateAddressRequestDTO {

	private final String profileID;
	private final String postcode;
	private final String address;
}
