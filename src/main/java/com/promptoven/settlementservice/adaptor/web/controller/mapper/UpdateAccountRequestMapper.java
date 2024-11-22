package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateAccountRequestVO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateAccountRequestDTO;

public class UpdateAccountRequestMapper {

	public static UpdateAccountRequestDTO toDTO(UpdateAccountRequestVO vo) {
		return UpdateAccountRequestDTO.builder()
			.profileID(vo.getProfileID())
			.accountID(vo.getAccountID())
			.bankName(vo.getBankName())
			.build();
	}
}
