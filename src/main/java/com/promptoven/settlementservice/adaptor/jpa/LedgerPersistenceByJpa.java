package com.promptoven.settlementservice.adaptor.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.adaptor.jpa.repository.SoldProductLedgerRepository;
import com.promptoven.settlementservice.application.port.out.call.LedgerPersistence;
import com.promptoven.settlementservice.application.service.dto.SoldProductLedgerDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LedgerPersistenceByJpa implements LedgerPersistence {

	private final SoldProductLedgerRepository soldProductLedgerRepository;

	@Override
	public void record(SoldProductLedgerDTO soldProductLedgerDTO) {

	}

	@Override
	public List<SoldProductLedgerDTO> get(Pair<LocalDateTime, LocalDateTime> range, String targetUUID) {
		return List.of();
	}

	@Override
	public List<SoldProductLedgerDTO> getUnsettled(String targetUUID) {
		return List.of();
	}
}
