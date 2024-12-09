package com.promptoven.settlementservice.adaptor.web.controller.mapper;

import java.time.LocalDate;

import org.springframework.data.util.Pair;

import com.promptoven.settlementservice.application.port.in.dto.SettlementHistoryRequestDTO;

public class SettlementHistoryRequestMapper {

	public static SettlementHistoryRequestDTO toDTO(String sellerUUID, Pair<LocalDate, LocalDate> range) {
		return SettlementHistoryRequestDTO.builder()
			.sellerUUID(sellerUUID)
			.beginDate(range.getFirst())
			.endDate(range.getSecond())
			.build();
	}
}
