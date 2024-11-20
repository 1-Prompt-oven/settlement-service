package com.promptoven.settlementservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AddressModelDTO {

	private final String postcode;
	private final String address;
}
