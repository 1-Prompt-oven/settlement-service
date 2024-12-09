package com.promptoven.settlementservice.application.port.out.call;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;

import com.promptoven.settlementservice.application.service.dto.SoldProductLedgerDTO;

public interface LedgerPersistence {

	void record(SoldProductLedgerDTO soldProductLedgerDTO);

	List<SoldProductLedgerDTO> get(Pair<LocalDate, LocalDate> range, String targetUUID);

	List<SoldProductLedgerDTO> getUnsettled(String targetUUID);

	void markSettle(SoldProductLedgerDTO soldProductLedgerDTO);
}
