package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import com.promptoven.settlementservice.adaptor.web.controller.vo.in.CreateSettlementProfileRequestVO;
import com.promptoven.settlementservice.application.port.in.dto.CreateSettlementProfileRequestDTO;

public class CreateSettlementProfileRequestMapper {

	public static CreateSettlementProfileRequestDTO toDTO(CreateSettlementProfileRequestVO vo) {
		return CreateSettlementProfileRequestDTO.builder()
			.memberID(vo.getMemberID())
			.taxID(vo.getTaxID())
			.accountID(vo.getAccountID())
			.bankName(vo.getBankName())
			.phone(vo.getPhone())
			.postcode(vo.getPostcode())
			.address(vo.getAddress())
			.build();
	}
}
