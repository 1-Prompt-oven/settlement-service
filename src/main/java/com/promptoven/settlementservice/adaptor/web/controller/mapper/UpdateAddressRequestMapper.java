package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateAddressRequestVO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateAddressRequestDTO;

public class UpdateAddressRequestMapper {

	public static UpdateAddressRequestDTO toDTO(UpdateAddressRequestVO vo) {
		return UpdateAddressRequestDTO.builder()
			.profileID(vo.getProfileID())
			.address(vo.getAddress())
			.postcode(vo.getPostcode())
			.build();
	}
}
