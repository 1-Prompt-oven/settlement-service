package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import com.promptoven.settlementservice.adaptor.web.controller.vo.out.SettlementProfileFullResponseVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.out.SettlementProfileResponseVO;
import com.promptoven.settlementservice.application.service.dto.SettlementProfileDTO;

public class SettlementProfileResponseMapper {

	public static SettlementProfileResponseVO toResponseVO(SettlementProfileDTO dto) {
		return SettlementProfileResponseVO.builder()
			.memberID(dto.getMemberID())
			.settlementProfileID(dto.getSettlementProfileID())
			.phone(dto.getPhone())
			.accountID(dto.getAccountID())
			.bankName(dto.getBankName())
			.postcode(dto.getPostcode())
			.address(dto.getAddress())
			.build();
	}

	public static SettlementProfileFullResponseVO toFullResponseVO(SettlementProfileDTO dto) {
		return SettlementProfileFullResponseVO.builder()
			.memberID(dto.getMemberID())
			.settlementProfileID(dto.getSettlementProfileID())
			.taxID(dto.getTaxID())
			.phone(dto.getPhone())
			.accountID(dto.getAccountID())
			.bankName(dto.getBankName())
			.postcode(dto.getPostcode())
			.address(dto.getAddress())
			.build();
	}
}
