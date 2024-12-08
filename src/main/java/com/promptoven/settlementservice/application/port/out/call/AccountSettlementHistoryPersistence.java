package com.promptoven.settlementservice.application.port.out.call;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;

import com.promptoven.settlementservice.application.service.dto.AccountSettlementHistoryDTO;

public interface AccountSettlementHistoryPersistence {

	void save(AccountSettlementHistoryDTO accountSettlementHistoryDTO);

	List<AccountSettlementHistoryDTO> get(String accountUUID, Pair<LocalDate, LocalDate> range);

	List<AccountSettlementHistoryDTO> prepareAdminReport(LocalDate targetDate);

	List<AccountSettlementHistoryDTO> prepareAdminReport(Pair<LocalDate, LocalDate> range);

	void saveAdminReport(List<AccountSettlementHistoryDTO> accountSettlementHistoryDTOs);
}
