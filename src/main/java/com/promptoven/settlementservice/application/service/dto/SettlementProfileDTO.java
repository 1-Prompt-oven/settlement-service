package com.promptoven.settlementservice.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SettlementProfileDTO {

	private String memberID;
	private String settlementProfileID;
	private String taxID;
	private String accountID;
	private String bankName;
	private String phone;
	private String postcode;
	private String address;

}
