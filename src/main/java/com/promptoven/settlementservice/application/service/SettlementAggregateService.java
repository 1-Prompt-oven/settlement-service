package com.promptoven.settlementservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.application.port.in.dto.LedgerAppendRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.SettlementHistoryRequestDTO;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementAggregateUsecase;
import com.promptoven.settlementservice.application.port.out.call.AccountSettlementHistoryPersistence;
import com.promptoven.settlementservice.application.port.out.call.LedgerPersistence;
import com.promptoven.settlementservice.application.service.dto.AccountSettlementHistoryDTO;
import com.promptoven.settlementservice.application.service.dto.mapper.SoldProductLedgerDomainDTOMapper;
import com.promptoven.settlementservice.domain.LedgerProductSelling;
import com.promptoven.settlementservice.domain.dto.LedgerProductSellingModelDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SettlementAggregateService implements SettlementAggregateUsecase {

	private final LedgerPersistence ledgerPersistence;
	private final AccountSettlementHistoryPersistence accountSettlementHistoryPersistence;
	private final SoldProductLedgerDomainDTOMapper soldProductLedgerDomainDTOMapper;

	@Override
	public List<AccountSettlementHistoryDTO> getAccountHistory(
		SettlementHistoryRequestDTO settlementHistoryRequestDTO) {
		return List.of();
	}

	@Override
	public void appendLedger(LedgerAppendRequestDTO ledgerAppendRequestDTO) {

		LedgerProductSellingModelDTO ledgerProductSellingModelDTO =
			convertLedgerAppendRequestDTOToLedgerProductSellingModelDTO(ledgerAppendRequestDTO);

		ledgerPersistence.record(
			soldProductLedgerDomainDTOMapper.toDTO(
				LedgerProductSelling.log(ledgerProductSellingModelDTO)));
	}

	private LedgerProductSellingModelDTO convertLedgerAppendRequestDTOToLedgerProductSellingModelDTO(
		LedgerAppendRequestDTO ledgerAppendRequestDTO) {
		return LedgerProductSellingModelDTO.builder()
			.sellerUUID(ledgerAppendRequestDTO.getSellerUUID())
			.productName(ledgerAppendRequestDTO.getProductName())
			.price(ledgerAppendRequestDTO.getPrice())
			.soldAt(ledgerAppendRequestDTO.getSoldAt())
			.build();
	}

	// todo: implement, make history and save, then settle ledger (fan-out:settle, fan-in:history)
	private void settleLedger(LedgerProductSelling ledgerProductSelling) {
		ledgerPersistence.record(
			soldProductLedgerDomainDTOMapper.toDTO(
				LedgerProductSelling.settle(ledgerProductSelling)));
	}
}
