package com.promptoven.settlementservice.adaptor.web.controller.vo.in;

import com.promptoven.settlementservice.application.port.in.dto.CreateSettlementProfileRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSettlementProfileRequestVO {

	private String memberID;
	private String taxID;
	private String accountID;
	private String bankName;
	private String phone;
	private String postcode;
	private String address;

	public CreateSettlementProfileRequestDTO toDTO() {
		return CreateSettlementProfileRequestDTO.builder()
			.memberID(memberID)
			.taxID(taxID)
			.accountID(accountID)
			.bankName(bankName)
			.phone(phone)
			.postcode(postcode)
			.address(address)
			.build();
	}
}
