package com.promptoven.settlementservice.application.port.out.call;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;

import com.promptoven.settlementservice.application.service.dto.AccountSettlementHistoryDTO;
import com.promptoven.settlementservice.application.service.dto.PlatformSettlementHistoryDTO;

public interface AccountSettlementHistoryPersistence {

	void save(AccountSettlementHistoryDTO accountSettlementHistoryDTO);

	List<AccountSettlementHistoryDTO> get(String accountUUID, Pair<LocalDate, LocalDate> range);

	List<AccountSettlementHistoryDTO> extractSourceForAdminReport(LocalDate targetDate);

	List<PlatformSettlementHistoryDTO> prepareAdminReport(Pair<LocalDate, LocalDate> range);

	void saveAdminReport(List<PlatformSettlementHistoryDTO> platformSettlementHistoryDTOS);
}
