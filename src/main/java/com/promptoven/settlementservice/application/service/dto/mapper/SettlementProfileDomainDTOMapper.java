package com.promptoven.settlementservice.application.service.dto.mapper;

import org.springframework.stereotype.Component;

import com.promptoven.settlementservice.application.service.dto.SettlementProfileDTO;
import com.promptoven.settlementservice.domain.SettlementProfile;

@Component
public class SettlementProfileDomainDTOMapper {

	public SettlementProfile toDomain(SettlementProfileDTO dto) {
		return SettlementProfile.builder()
			.settlementProfileID(dto.getSettlementProfileID())
			.taxID(dto.getTaxID())
			.phone(dto.getPhone())
			.accountID(dto.getAccountID())
			.bankName(dto.getBankName())
			.memberID(dto.getMemberID())
			.address(dto.getAddress())
			.postcode(dto.getPostcode())
			.build();
	}

	public SettlementProfileDTO toDTO(SettlementProfile domain) {
		return SettlementProfileDTO.builder()
			.settlementProfileID(domain.getSettlementProfileID())
			.taxID(domain.getTaxID())
			.phone(domain.getPhone())
			.accountID(domain.getAccountID())
			.bankName(domain.getBankName())
			.memberID(domain.getMemberID())
			.address(domain.getAddress())
			.postcode(domain.getPostcode())
			.build();
	}
}
