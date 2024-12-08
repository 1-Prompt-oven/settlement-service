package com.promptoven.settlementservice.adaptor.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.adaptor.jpa.repository.AccountSettlementHistoryRepository;
import com.promptoven.settlementservice.adaptor.jpa.repository.PlatformSettlementHistoryRepository;
import com.promptoven.settlementservice.application.port.out.call.AccountSettlementHistoryPersistence;
import com.promptoven.settlementservice.application.service.dto.AccountSettlementHistoryDTO;
import com.promptoven.settlementservice.application.service.dto.PlatformSettlementHistoryDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountSettlementHistoryPersistenceByJpa implements AccountSettlementHistoryPersistence {

	private final AccountSettlementHistoryRepository accountSettlementHistoryRepository;
	private final PlatformSettlementHistoryRepository platformSettlementHistoryRepository;

	@Override
	public void save(AccountSettlementHistoryDTO accountSettlementHistoryDTO) {

	}

	@Override
	public List<AccountSettlementHistoryDTO> get(String accountUUID, Pair<LocalDate, LocalDate> range) {
		return List.of();
	}

	@Override
	public List<AccountSettlementHistoryDTO> extractSourceForAdminReport(LocalDate targetDate) {
		return List.of();
	}

	@Override
	public List<PlatformSettlementHistoryDTO> prepareAdminReport(Pair<LocalDate, LocalDate> range) {
		return List.of();
	}

	@Override
	public void saveAdminReport(List<PlatformSettlementHistoryDTO> platformSettlementHistoryDTOS) {

	}
}
