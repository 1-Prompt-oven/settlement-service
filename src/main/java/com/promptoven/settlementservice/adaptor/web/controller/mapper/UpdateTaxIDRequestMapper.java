package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateTaxIDRequestVO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateTaxIDRequestDTO;

public class UpdateTaxIDRequestMapper {

	public static UpdateTaxIDRequestDTO toDTO(UpdateTaxIDRequestVO vo) {
		return UpdateTaxIDRequestDTO.builder()
			.profileID(vo.getProfileID())
			.taxID(vo.getTaxID())
			.build();
	}
}
