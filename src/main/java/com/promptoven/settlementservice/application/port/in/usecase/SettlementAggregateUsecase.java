package com.promptoven.settlementservice.application.port.in.usecase;

import java.util.List;

import com.promptoven.settlementservice.application.port.in.dto.LedgerAppendRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.SettlementHistoryRequestDTO;
import com.promptoven.settlementservice.application.service.dto.AccountSettlementHistoryDTO;

public interface SettlementAggregateUsecase {

	List<AccountSettlementHistoryDTO> getAccountHistory(SettlementHistoryRequestDTO settlementHistoryRequestDTO);

	void appendLedger(LedgerAppendRequestDTO ledgerAppendRequestDTO);
}
