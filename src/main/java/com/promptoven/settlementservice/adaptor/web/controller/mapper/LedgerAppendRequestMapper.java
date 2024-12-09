package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import com.promptoven.settlementservice.adaptor.web.controller.vo.in.LedgerAppendRequestVO;
import com.promptoven.settlementservice.application.port.in.dto.LedgerAppendRequestDTO;

public class LedgerAppendRequestMapper {

	public static LedgerAppendRequestDTO toDTO(LedgerAppendRequestVO ledgerAppendRequestVO) {
		return LedgerAppendRequestDTO.builder()
			.sellerUUID(ledgerAppendRequestVO.getSellerUUID())
			.productName(ledgerAppendRequestVO.getProductName())
			.price(ledgerAppendRequestVO.getPrice())
			.soldAt(ledgerAppendRequestVO.getSoldAt())
			.build();
	}
}
