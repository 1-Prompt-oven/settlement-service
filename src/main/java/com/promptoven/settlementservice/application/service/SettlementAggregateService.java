package com.promptoven.settlementservice.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.application.port.in.dto.LedgerAppendRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.SettlementHistoryRequestDTO;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementAggregateUsecase;
import com.promptoven.settlementservice.application.port.out.call.AccountSettlementHistoryPersistence;
import com.promptoven.settlementservice.application.port.out.call.LedgerPersistence;
import com.promptoven.settlementservice.application.port.out.call.SettlementProfilePersistence;
import com.promptoven.settlementservice.application.service.dto.AccountSettlementHistoryDTO;
import com.promptoven.settlementservice.application.service.dto.PlatformSettlementHistoryDTO;
import com.promptoven.settlementservice.application.service.dto.SoldProductLedgerDTO;
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
	private final SettlementProfilePersistence settlementProfilePersistence;

	@Override
	public List<AccountSettlementHistoryDTO> getAccountHistory(
		SettlementHistoryRequestDTO settlementHistoryRequestDTO) {
		Pair<LocalDate, LocalDate> range = Pair.of(settlementHistoryRequestDTO.getBeginDate(),
			settlementHistoryRequestDTO.getEndDate());
		return accountSettlementHistoryPersistence.get(settlementHistoryRequestDTO.getSellerUUID(), range);
	}

	@Override
	public List<PlatformSettlementHistoryDTO> getAdminHistory(SettlementHistoryRequestDTO settlementHistoryRequestDTO) {
		Pair<LocalDate, LocalDate> range = Pair.of(settlementHistoryRequestDTO.getBeginDate(),
			settlementHistoryRequestDTO.getEndDate());
		return accountSettlementHistoryPersistence.prepareAdminReport(range);
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
	@Scheduled(cron = "0 5 0 * * *") // Runs at 00:05 UTC daily
	private void settleLedger() {

		LocalDate targetDate = LocalDate.now().minusDays(1);
		Long accumulatedSold = 0L;
		Long accumulatedTaxOfPlatform = 0L;
		Long accumulatedTaxOfSeller = 0L;
		Long accumulatedPlatformCharge = 0L;

		// 우선 사용자의 판매내역들을 불러와야 한다.
		List<String> sellerUUIDList = settlementProfilePersistence.getSellerUUIDs();

		// 그리고 그 사용자의 판매내역들을 정산해야 한다.
		// 그리고 정산된 내역을 저장해야 한다.
		// 그리고 정산 기록을 근거로 일일 매출, 일일 수익금, 일일 세금 및 플랫폼 수수료를 계산한다.
		// 플랫폼 수수료 10%, 세금은 대한민국 세법을 따라 기타소득 범위(매출 500만원 이내)는 8.8%,
		// 사업소득범위 (매출 초과 or 사업자등록번호 대상 정산)은 3.3%이다.
		// 그리고 그것을 저장해야 한다. 사용자에게는 세금및 수수료를 제외한 수익금만 기록하여 전달한다. 다만 DB에는 전체 기록한다.
		// 플랫폼의 관점에서 일일 총 매출, 일일 총 수익금, 일일 총 세금 및 플랫폼 수수료를 계산한다.

	}

	private AccountSettlementHistoryDTO logAccountSettlementHistory(String sellerUUID, LocalDate targetDate) {

		List<SoldProductLedgerDTO> soldProductLedgerDTOList = ledgerPersistence.getUnsettled(sellerUUID);
		return null;
	}
}
