package com.promptoven.settlementservice.adaptor.infrastructure.web.controller.vo.out;

import com.promptoven.settlementservice.domain.SettlementProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SettlementProfileResponseVO {

	private String memberID;
	private String settlementProfileID;
	// Tax ID must be Encrypted
	private String taxID;
	private String accountID;
	private String bankName;
	private String phone;
	private String postcode;
	private String address;

	public static SettlementProfileResponseVO fromDomain(SettlementProfile settlementProfile) {
		return SettlementProfileResponseVO.builder()
			.memberID(settlementProfile.getMemberID())
			.settlementProfileID(settlementProfile.getSettlementProfileID())
			.taxID(settlementProfile.getTaxID())
			.accountID(settlementProfile.getAccountID())
			.bankName(settlementProfile.getBankName())
			.phone(settlementProfile.getPhone())
			.postcode(settlementProfile.getPostcode())
			.address(settlementProfile.getAddress())
			.build();
	}
}
