package com.promptoven.settlementservice.adaptor.infrastructure.web.controller.vo.out;

import com.promptoven.settlementservice.application.port.out.dto.SettlementProfileFullInfoDTO;

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

	public static SettlementProfileFullResponseVO fromDTO(SettlementProfileFullInfoDTO settlementProfileFullInfoDTO) {
		return SettlementProfileFullResponseVO.builder()
			.memberID(settlementProfileFullInfoDTO.getMemberID())
			.settlementProfileID(settlementProfileFullInfoDTO.getSettlementProfileID())
			.taxID(settlementProfileFullInfoDTO.getTaxID())
			.accountID(settlementProfileFullInfoDTO.getAccountID())
			.bankName(settlementProfileFullInfoDTO.getBankName())
			.phone(settlementProfileFullInfoDTO.getPhone())
			.postcode(settlementProfileFullInfoDTO.getPostcode())
			.address(settlementProfileFullInfoDTO.getAddress())
			.build();
	}

}
