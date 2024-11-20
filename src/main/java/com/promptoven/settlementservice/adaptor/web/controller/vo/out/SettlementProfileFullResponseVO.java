package com.promptoven.settlementservice.adaptor.web.controller.vo.out;

import com.promptoven.settlementservice.application.port.out.dto.SettlementProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SettlementProfileFullResponseVO {

	private String memberID;
	private String settlementProfileID;
	// decrypted taxID
	private String taxID;
	private String accountID;
	private String bankName;
	private String phone;
	private String postcode;
	private String address;

	public static SettlementProfileFullResponseVO fromDTO(SettlementProfileDTO settlementProfileDTO) {
		return SettlementProfileFullResponseVO.builder()
			.memberID(settlementProfileDTO.getMemberID())
			.settlementProfileID(settlementProfileDTO.getSettlementProfileID())
			.taxID(settlementProfileDTO.getTaxID())
			.accountID(settlementProfileDTO.getAccountID())
			.bankName(settlementProfileDTO.getBankName())
			.phone(settlementProfileDTO.getPhone())
			.postcode(settlementProfileDTO.getPostcode())
			.address(settlementProfileDTO.getAddress())
			.build();
	}

}
