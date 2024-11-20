package com.promptoven.settlementservice.adaptor.jpa;

import com.promptoven.settlementservice.adaptor.jpa.entity.SettlementProfileEntity;
import com.promptoven.settlementservice.application.port.out.dto.SettlementProfileDTO;

class JpaSettlementProfileDTOEntityMapper {

	public static SettlementProfileEntity fromDTO(SettlementProfileDTO settlementProfileDTO) {
		return SettlementProfileEntity.builder()
			.settlementProfileID(settlementProfileDTO.getSettlementProfileID())
			.memberID(settlementProfileDTO.getMemberID())
			.taxID(settlementProfileDTO.getTaxID())
			.bankName(settlementProfileDTO.getBankName())
			.accountID(settlementProfileDTO.getAccountID())
			.phone(settlementProfileDTO.getPhone())
			.address(settlementProfileDTO.getAddress())
			.postcode(settlementProfileDTO.getPostcode())
			.build();
	}

	public static SettlementProfileDTO toDTO(SettlementProfileEntity settlementProfileEntity) {
		return SettlementProfileDTO.builder()
			.settlementProfileID(settlementProfileEntity.getSettlementProfileID())
			.memberID(settlementProfileEntity.getMemberID())
			.taxID(settlementProfileEntity.getTaxID())
			.bankName(settlementProfileEntity.getBankName())
			.accountID(settlementProfileEntity.getAccountID())
			.phone(settlementProfileEntity.getPhone())
			.address(settlementProfileEntity.getAddress())
			.postcode(settlementProfileEntity.getPostcode())
			.build();
	}
}
