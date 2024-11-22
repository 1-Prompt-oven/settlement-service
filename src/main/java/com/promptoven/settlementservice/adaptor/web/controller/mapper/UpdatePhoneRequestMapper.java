package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdatePhoneRequestVO;
import com.promptoven.settlementservice.application.port.in.dto.UpdatePhoneRequestDTO;

public class UpdatePhoneRequestMapper {

	public static UpdatePhoneRequestDTO toDTO(UpdatePhoneRequestVO vo) {
		return UpdatePhoneRequestDTO.builder()
			.profileID(vo.getProfileID())
			.phone(vo.getPhone())
			.build();
	}
}
