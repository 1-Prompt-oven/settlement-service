package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import com.promptoven.settlementservice.adaptor.web.controller.vo.out.AdminSettlementHistoryResponseVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.out.SettlementHistoryResponseVO;
import com.promptoven.settlementservice.application.service.dto.AccountSettlementHistoryDTO;
import com.promptoven.settlementservice.application.service.dto.PlatformSettlementHistoryDTO;

public class SettlementHistoryResponseMapper {

	public static SettlementHistoryResponseVO toVO(AccountSettlementHistoryDTO accountSettlementHistoryDTO) {
		return SettlementHistoryResponseVO.builder()
			.sellerUUID(accountSettlementHistoryDTO.getSellerUUID())
			.targetDate(accountSettlementHistoryDTO.getRecordedAt())
			.dailyEarned(accountSettlementHistoryDTO.getAccumulatedEarned())
			.dailySold(accountSettlementHistoryDTO.getAccumulatedSold())
			.build();
	}

	public static AdminSettlementHistoryResponseVO toAdminVO(
		PlatformSettlementHistoryDTO platformSettlementHistoryDTO) {
		return AdminSettlementHistoryResponseVO.builder()
			.targetDate(platformSettlementHistoryDTO.getRecordedAt())
			.accumulatedEarned(platformSettlementHistoryDTO.getAccumulatedEarned())
			.accumulatedSettledForAdminTax(platformSettlementHistoryDTO.getAccumulatedSettledForAdminTax())
			.accumulatedSettledForSellerTax(platformSettlementHistoryDTO.getAccumulatedSettledForSellerTax())
			.accumulatedSold(platformSettlementHistoryDTO.getAccumulatedSold())
			.build();
	}
}
