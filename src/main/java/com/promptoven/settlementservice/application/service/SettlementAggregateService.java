package com.promptoven.settlementservice.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.settlementservice.application.port.in.dto.LedgerAppendRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.SettlementHistoryRequestDTO;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementAggregateUsecase;
import com.promptoven.settlementservice.application.port.out.call.AccountSettlementHistoryPersistence;
import com.promptoven.settlementservice.application.port.out.call.LedgerPersistence;
import com.promptoven.settlementservice.application.port.out.call.MailSending;
import com.promptoven.settlementservice.application.port.out.call.SettlementProfilePersistence;
import com.promptoven.settlementservice.application.service.dto.AccountSettlementHistoryDTO;
import com.promptoven.settlementservice.application.service.dto.PlatformSettlementHistoryDTO;
import com.promptoven.settlementservice.application.service.dto.SoldProductLedgerDTO;
import com.promptoven.settlementservice.application.service.dto.mapper.SoldProductLedgerDomainDTOMapper;
import com.promptoven.settlementservice.application.util.Encrypter;
import com.promptoven.settlementservice.application.util.KoreaTaxRateTable;
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
	private final KoreaTaxRateTable koreaTaxRateTable;
	private final Encrypter encrypter;
	private final MailSending mailSending;

	@Value("${promptoven.charge-rate}")
	double chargeRate;

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

	@Override
	public void appendSuspendedUserLedger(LedgerAppendRequestDTO ledgerAppendRequestDTO) {
		LedgerProductSellingModelDTO ledgerProductSellingModelDTO =
			convertLedgerAppendRequestDTOToLedgerProductSellingModelDTO(ledgerAppendRequestDTO);

		ledgerPersistence.recordSuspend(
			soldProductLedgerDomainDTOMapper.toDTO(
				LedgerProductSelling.log(ledgerProductSellingModelDTO)));
	}

	@Override
	public void appendPlatformLedger(LedgerAppendRequestDTO ledgerAppendRequestDTO) {
		LedgerProductSellingModelDTO ledgerProductSellingModelDTO =
			convertLedgerAppendRequestDTOToLedgerProductSellingModelDTO(ledgerAppendRequestDTO);

		ledgerPersistence.record(
			soldProductLedgerDomainDTOMapper.toDTO(
				LedgerProductSelling.log(ledgerProductSellingModelDTO)));
	}

	@Override
	public void appendSuspendedPlatformLedger(LedgerAppendRequestDTO ledgerAppendRequestDTO) {
		LedgerProductSellingModelDTO ledgerProductSellingModelDTO =
			convertLedgerAppendRequestDTOToLedgerProductSellingModelDTO(ledgerAppendRequestDTO);

		ledgerPersistence.recordSuspend(
			soldProductLedgerDomainDTOMapper.toDTO(
				LedgerProductSelling.log(ledgerProductSellingModelDTO)));
	}

	@Override
	public void unSuspend(LedgerAppendRequestDTO ledgerAppendRequestDTO) {
		LedgerProductSellingModelDTO ledgerProductSellingModelDTO =
			convertLedgerAppendRequestDTOToLedgerProductSellingModelDTO(ledgerAppendRequestDTO);

		ledgerPersistence.markUnSuspend(
			soldProductLedgerDomainDTOMapper.toDTO(
				LedgerProductSelling.log(ledgerProductSellingModelDTO)));
	}

	@Override
	@Transactional
	public void testSchedule() {
		LocalDate targetDate = LocalDate.now().minusDays(1);
		LocalDate refererDate = targetDate.minusDays(1);
		List<PlatformSettlementHistoryDTO> previousPlatformData =
			accountSettlementHistoryPersistence.prepareAdminReport(Pair.of(refererDate, targetDate));
		if (previousPlatformData.isEmpty()) {
			settleLedger(null);
		}
	}

	private LedgerProductSellingModelDTO convertLedgerAppendRequestDTOToLedgerProductSellingModelDTO(
		LedgerAppendRequestDTO ledgerAppendRequestDTO) {
		return LedgerProductSellingModelDTO.builder()
			.sellerUUID(ledgerAppendRequestDTO.getSellerUUID())
			.productName(ledgerAppendRequestDTO.getProductName())
			.price(ledgerAppendRequestDTO.getPrice())
			.soldAt(ledgerAppendRequestDTO.getSoldAt())
			.orderID(ledgerAppendRequestDTO.getOrderID())
			.build();
	}

	@Transactional
	@Scheduled(cron = "0 0 0 * * *") // Runs at 01:00 UTC daily
	public void settleLedger(@Nullable LocalDate target) {
		log.info("settlement system start daily aggregation");
		LocalDate targetDate = LocalDate.now().minusDays(1);
		if (null != target) {
			targetDate = target;
		}
		LocalDate refererDate = targetDate.minusDays(1);

		long accumulatedSold = 0L;
		long accumulatedTaxOfSeller = 0L;
		long accumulatedPlatformCharge = 0L;

		List<String> sellerUUIDList = settlementProfilePersistence.getSellerUUIDs();

		// Process each seller's settlement
		for (String sellerUUID : sellerUUIDList) {
			List<AccountSettlementHistoryDTO> previousSellerData =
				accountSettlementHistoryPersistence.get(sellerUUID, Pair.of(refererDate, targetDate));
			AccountSettlementHistoryDTO previousData =
				previousSellerData.isEmpty() ? createEmptySellerSettlement(sellerUUID, refererDate) :
					previousSellerData.getFirst();

			AccountSettlementHistoryDTO sellerSettlement = logAccountSettlementHistory(sellerUUID, previousData,
				targetDate);

			if (sellerSettlement != null) {
				accumulatedSold += sellerSettlement.getAccumulatedSold();
				accumulatedTaxOfSeller +=
					sellerSettlement.getThisYearNationalTax() + sellerSettlement.getThisYearLocalTax();
				accumulatedPlatformCharge += sellerSettlement.getThisYearPlatformCharge();
			}
		}

		accumulatedPlatformCharge += platformSalesTrace();

		long accumulatedTaxOfPlatform = (long)(calculatePlatformTax(accumulatedPlatformCharge) * (1
			+ koreaTaxRateTable.koreaRegionalTaxRate));

		// Get previous platform settlement data
		List<PlatformSettlementHistoryDTO> previousPlatformData =
			accountSettlementHistoryPersistence.prepareAdminReport(Pair.of(refererDate, targetDate));
		PlatformSettlementHistoryDTO previousSettlement =
			previousPlatformData.isEmpty() ? createEmptyPlatformSettlement(refererDate) :
				previousPlatformData.getFirst();

		// Create platform-wide settlement history builder
		PlatformSettlementHistoryDTO.PlatformSettlementHistoryDTOBuilder builder =
			PlatformSettlementHistoryDTO.builder()
				.recordedAt(targetDate);

		if (targetDate.getDayOfYear() == 1) {
			// Reset yearly calculations on January 1st
			builder.accumulatedSold(previousSettlement.getAccumulatedSold() + accumulatedSold)
				.accumulatedSettledForAdminTax(accumulatedTaxOfPlatform)
				.accumulatedSettledForSellerTax(accumulatedTaxOfSeller)
				.accumulatedEarned(accumulatedPlatformCharge)
				.thisYearSettledForAdminTax(accumulatedTaxOfPlatform)
				.thisYearSettledForSellerTax(accumulatedTaxOfSeller)
				.thisYearEarned(accumulatedPlatformCharge);
		} else {
			// Add to existing yearly calculations
			builder.accumulatedSold(previousSettlement.getAccumulatedSold() + accumulatedSold)
				.accumulatedSettledForAdminTax(
					previousSettlement.getAccumulatedSettledForAdminTax() + accumulatedTaxOfPlatform)
				.accumulatedSettledForSellerTax(
					previousSettlement.getAccumulatedSettledForSellerTax() + accumulatedTaxOfSeller)
				.accumulatedEarned(previousSettlement.getAccumulatedEarned() + accumulatedPlatformCharge)
				.thisYearSettledForAdminTax(
					previousSettlement.getThisYearSettledForAdminTax() + accumulatedTaxOfPlatform)
				.thisYearSettledForSellerTax(
					previousSettlement.getThisYearSettledForSellerTax() + accumulatedTaxOfSeller)
				.thisYearEarned(previousSettlement.getThisYearEarned() + accumulatedPlatformCharge);
		}

		PlatformSettlementHistoryDTO platformSettlementHistoryDTO = builder.build();
		// Save platform settlement history
		accountSettlementHistoryPersistence.saveAdminReport(List.of(platformSettlementHistoryDTO));
		CompletableFuture.runAsync(() -> mailSending.sendMail(platformSettlementHistoryDTO));

	}

	private PlatformSettlementHistoryDTO createEmptyPlatformSettlement(LocalDate date) {
		return PlatformSettlementHistoryDTO.builder()
			.recordedAt(date)
			.accumulatedSold(0L)
			.accumulatedSettledForAdminTax(0L)
			.accumulatedSettledForSellerTax(0L)
			.accumulatedEarned(0L)
			.thisYearSettledForAdminTax(0L)
			.thisYearSettledForSellerTax(0L)
			.thisYearEarned(0L)
			.build();
	}

	@Transactional
	public AccountSettlementHistoryDTO logAccountSettlementHistory(
		String sellerUUID, AccountSettlementHistoryDTO previousData, LocalDate targetDate) {

		List<SoldProductLedgerDTO> unsettledLedgers = ledgerPersistence.getUnsettled(sellerUUID);

		if (unsettledLedgers.isEmpty()) {
			return null;
		}

		long totalSales = calculateTotalSales(unsettledLedgers);
		long platformCharge = calculatePlatformCharge(totalSales);
		Pair<Long, Long> sellerTaxes = calculateSellerTax(totalSales, sellerUUID);
		long sellerNationalTax = sellerTaxes.getFirst();
		long sellerRegionalTax = sellerTaxes.getSecond();
		long earned = totalSales - platformCharge - sellerNationalTax - sellerRegionalTax;

		// Add null checks when accessing previousData
		long prevAccumulatedSold = previousData.getAccumulatedSold() != null ? previousData.getAccumulatedSold() : 0L;
		long prevAccumulatedEarned =
			previousData.getAccumulatedEarned() != null ? previousData.getAccumulatedEarned() : 0L;
		long prevAccumulatedSettled =
			previousData.getAccumulatedSettled() != null ? previousData.getAccumulatedSettled() : 0L;
		long prevYearLocalTax = previousData.getThisYearLocalTax() != null ? previousData.getThisYearLocalTax() : 0L;
		long prevYearlyEarned = previousData.getThisYearlyEarned() != null ? previousData.getThisYearlyEarned() : 0L;
		long prevYearNationalTax =
			previousData.getThisYearNationalTax() != null ? previousData.getThisYearNationalTax() : 0L;
		long prevYearPlatformCharge =
			previousData.getThisYearPlatformCharge() != null ? previousData.getThisYearPlatformCharge() : 0L;

		AccountSettlementHistoryDTO.AccountSettlementHistoryDTOBuilder builder = AccountSettlementHistoryDTO.builder()
			.sellerUUID(sellerUUID)
			.recordedAt(targetDate)
			.accumulatedSold(prevAccumulatedSold + totalSales)
			.accumulatedEarned(prevAccumulatedEarned + earned)
			.accumulatedSettled(prevAccumulatedSettled);

		if (targetDate.getDayOfYear() == 1) {
			// Reset yearly calculations on January 1st
			builder.thisYearLocalTax(sellerRegionalTax)
				.thisYearlyEarned(earned)
				.thisYearNationalTax(sellerNationalTax)
				.thisYearPlatformCharge(platformCharge);
		} else {
			// Add to existing yearly calculations
			builder.thisYearLocalTax(prevYearLocalTax + sellerRegionalTax)
				.thisYearlyEarned(prevYearlyEarned + earned)
				.thisYearNationalTax(prevYearNationalTax + sellerNationalTax)
				.thisYearPlatformCharge(prevYearPlatformCharge + platformCharge);
		}

		AccountSettlementHistoryDTO settlement = builder.build();

		// Save settlement history
		accountSettlementHistoryPersistence.save(settlement);

		// Mark ledgers as settled
		unsettledLedgers.stream().map(soldProductLedgerDomainDTOMapper::toDomain)
			.map(LedgerProductSelling::settle)
			.map(soldProductLedgerDomainDTOMapper::toDTO)
			.forEach(ledgerPersistence::markSettle);

		return settlement;
	}

	private long calculateTotalSales(List<SoldProductLedgerDTO> ledgers) {
		return ledgers.stream()
			.mapToLong(SoldProductLedgerDTO::getPrice)
			.sum();
	}

	private long calculatePlatformCharge(long totalSales) {
		return (long)(totalSales * chargeRate); // 10% platform fee
	}

	private Pair<Long, Long> calculateSellerTax(long totalSales, String sellerUUID) {
		String encryptedTaxID = settlementProfilePersistence.getTaxID(sellerUUID);
		boolean isBusinessRegistered = true;
		try {
			String decryptedTaxID = encrypter.decrypt(encryptedTaxID);
			if (decryptedTaxID.length() != 13) { //PID(주민등록번호) is 13 digit number
				isBusinessRegistered = false;
			}
		} catch (Exception ignored) {
		}

		if (isBusinessRegistered || totalSales > koreaTaxRateTable.koreaIndividualBusinessBorder) {
			long nationalTax = (long)(totalSales * koreaTaxRateTable.koreaIndividualBusinessRate);
			long regionalTax = (long)(nationalTax * koreaTaxRateTable.koreaRegionalTaxRate);
			return Pair.of(nationalTax, regionalTax);
		} else if (totalSales < koreaTaxRateTable.koreaOtherIgnoreBorder) {
			return Pair.of(0L, 0L);
		} else {
			long nationalTax = (long)(totalSales * koreaTaxRateTable.koreaIndividualOtherRate);
			long regionalTax = (long)(nationalTax * koreaTaxRateTable.koreaRegionalTaxRate);
			return Pair.of(nationalTax, regionalTax);
		}
	}

	private long calculatePlatformTax(long platformCharge) {
		if (platformCharge <= koreaTaxRateTable.koreaCooperationLevel0Border) {
			return (long)(platformCharge * koreaTaxRateTable.koreaCooperationLevel0Rate);
		} else if (platformCharge <= koreaTaxRateTable.koreaCooperationLevel1Border) {
			return (long)((platformCharge - koreaTaxRateTable.koreaCooperationLevel1Exclude)
				* koreaTaxRateTable.koreaCooperationLevel1Rate
				+ koreaTaxRateTable.koreaCooperationLevel1Include);
		} else if (platformCharge <= koreaTaxRateTable.koreaCooperationLevel2Border) {
			return (long)((platformCharge - koreaTaxRateTable.koreaCooperationLevel2Exclude)
				* koreaTaxRateTable.koreaCooperationLevel2Rate
				+ koreaTaxRateTable.koreaCooperationLevel2Include);
		} else {
			return (long)((platformCharge - koreaTaxRateTable.koreaCooperationLevel3Exclude)
				* koreaTaxRateTable.koreaCooperationLevel3Rate
				+ koreaTaxRateTable.koreaCooperationLevel3Include);
		}
	}

	private AccountSettlementHistoryDTO createEmptySellerSettlement(String sellerUUID, LocalDate date) {
		return AccountSettlementHistoryDTO.builder()
			.sellerUUID(sellerUUID)
			.recordedAt(date)
			.accumulatedSold(0L)
			.accumulatedEarned(0L)
			.accumulatedSettled(0L)
			.thisYearLocalTax(0L)
			.thisYearlyEarned(0L)
			.thisYearNationalTax(0L)
			.thisYearPlatformCharge(0L)
			.build();
	}

	private long platformSalesTrace() {
		long accumulation = 0L;

		List<SoldProductLedgerDTO> unsettledLedgers = ledgerPersistence.getUnsettled("admin");

		if (unsettledLedgers.isEmpty()) {
			return accumulation;
		}

		accumulation = calculateTotalSales(unsettledLedgers);

		// Mark ledgers as settled
		unsettledLedgers.stream().map(soldProductLedgerDomainDTOMapper::toDomain)
			.map(LedgerProductSelling::settle)
			.map(soldProductLedgerDomainDTOMapper::toDTO)
			.forEach(ledgerPersistence::markSettle);
		return accumulation;
	}
}
