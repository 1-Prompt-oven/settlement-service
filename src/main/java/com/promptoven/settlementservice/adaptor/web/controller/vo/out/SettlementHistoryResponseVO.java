package com.promptoven.settlementservice.adaptor.web.controller.vo.out;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SettlementHistoryResponseVO {

	private String sellerUUID;
	private LocalDate targetDate;
	private Long dailySold;
	private Long dailyEarned;
	
}
