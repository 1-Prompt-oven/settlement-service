package com.promptoven.settlementservice.application.port.out.call;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.util.Pair;

import com.promptoven.settlementservice.application.service.dto.SoldProductLedgerDTO;

public interface LedgerPersistence {

	void record(SoldProductLedgerDTO soldProductLedgerDTO);

	List<SoldProductLedgerDTO> get(Pair<LocalDateTime, LocalDateTime> range, String targetUUID);

	List<SoldProductLedgerDTO> getUnsettled(String targetUUID);
}
