package com.promptoven.settlementservice.adaptor.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.adaptor.jpa.entity.AccountSettlementHistoryEntity;
import com.promptoven.settlementservice.adaptor.jpa.entity.PlatformSettlementHistoryEntity;
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
		accountSettlementHistoryRepository.save(AccountHistoryDtoEntityMapper.toEntity(accountSettlementHistoryDTO));
	}

	@Override
	public List<AccountSettlementHistoryDTO> get(String accountUUID, Pair<LocalDate, LocalDate> range) {
		LocalDate beginDate = range.getFirst();
		LocalDate endDate = range.getSecond();
		return accountSettlementHistoryRepository.findBySellerUUIDAndRecordedAtBetween(accountUUID, beginDate, endDate)
			.stream()
			.map(AccountHistoryDtoEntityMapper::toDTO)
			.toList();
	}

	@Override
	public List<AccountSettlementHistoryDTO> extractSourceForAdminReport(LocalDate targetDate) {
		return accountSettlementHistoryRepository.findByRecordedAt(targetDate).stream()
			.map(AccountHistoryDtoEntityMapper::toDTO)
			.toList();
	}

	@Override
	public List<PlatformSettlementHistoryDTO> prepareAdminReport(Pair<LocalDate, LocalDate> range) {
		LocalDate beginDate = range.getFirst();
		LocalDate endDate = range.getSecond();

		List<PlatformSettlementHistoryEntity> platformSettlementHistoryEntities =
			platformSettlementHistoryRepository.findByRecordedAtBetween(beginDate, endDate);
		return platformSettlementHistoryEntities.stream()
			.map(PlatformHistoryDtoEntityMapper::toDTO)
			.toList();
	}

	@Override
	public void saveAdminReport(List<PlatformSettlementHistoryDTO> platformSettlementHistoryDTOS) {
		platformSettlementHistoryDTOS.forEach(platformSettlementHistoryDTO -> {
			platformSettlementHistoryRepository.save(
				PlatformHistoryDtoEntityMapper.toEntity(platformSettlementHistoryDTO));
		});
	}
}

class AccountHistoryDtoEntityMapper {
	public static AccountSettlementHistoryDTO toDTO(AccountSettlementHistoryEntity accountSettlementHistoryEntity) {
		return AccountSettlementHistoryDTO.builder()
			.recordedAt(accountSettlementHistoryEntity.getRecordedAt())
			.sellerUUID(accountSettlementHistoryEntity.getSellerUUID())
			.accumulatedEarned(accountSettlementHistoryEntity.getAccumulatedEarned())
			.accumulatedSettled(accountSettlementHistoryEntity.getAccumulatedSettled())
			.accumulatedSold(accountSettlementHistoryEntity.getAccumulatedSold())
			.thisYearlyEarned(accountSettlementHistoryEntity.getThisYearlyEarned())
			.build();
	}

	public static AccountSettlementHistoryEntity toEntity(AccountSettlementHistoryDTO accountSettlementHistoryDTO) {
		return AccountSettlementHistoryEntity.builder()
			.recordedAt(accountSettlementHistoryDTO.getRecordedAt())
			.sellerUUID(accountSettlementHistoryDTO.getSellerUUID())
			.accumulatedEarned(accountSettlementHistoryDTO.getAccumulatedEarned())
			.accumulatedSettled(accountSettlementHistoryDTO.getAccumulatedSettled())
			.accumulatedSold(accountSettlementHistoryDTO.getAccumulatedSold())
			.thisYearlyEarned(accountSettlementHistoryDTO.getThisYearlyEarned())
			.build();
	}
}

class PlatformHistoryDtoEntityMapper {
	public static PlatformSettlementHistoryDTO toDTO(PlatformSettlementHistoryEntity platformSettlementHistoryEntity) {
		return PlatformSettlementHistoryDTO.builder()
			.recordedAt(platformSettlementHistoryEntity.getRecordedAt())
			.accumulatedSold(platformSettlementHistoryEntity.getAccumulatedSold())
			.accumulatedEarned(platformSettlementHistoryEntity.getAccumulatedEarned())
			.accumulatedSettledForSellerTax(platformSettlementHistoryEntity.getAccumulatedSettledForSellerTax())
			.accumulatedSettledForAdminTax(platformSettlementHistoryEntity.getAccumulatedSettledForAdminTax())
			.thisYearSettledForAdminTax(platformSettlementHistoryEntity.getThisYearSettledForAdminTax())
			.thisYearSettledForSellerTax(platformSettlementHistoryEntity.getThisYearSettledForSellerTax())
			.thisYearEarned(platformSettlementHistoryEntity.getThisYearEarned())
			.build();
	}

	public static PlatformSettlementHistoryEntity toEntity(PlatformSettlementHistoryDTO platformSettlementHistoryDTO) {
		return PlatformSettlementHistoryEntity.builder()
			.recordedAt(platformSettlementHistoryDTO.getRecordedAt())
			.accumulatedSold(platformSettlementHistoryDTO.getAccumulatedSold())
			.accumulatedEarned(platformSettlementHistoryDTO.getAccumulatedEarned())
			.accumulatedSettledForSellerTax(platformSettlementHistoryDTO.getAccumulatedSettledForSellerTax())
			.accumulatedSettledForAdminTax(platformSettlementHistoryDTO.getAccumulatedSettledForAdminTax())
			.thisYearSettledForAdminTax(platformSettlementHistoryDTO.getThisYearSettledForAdminTax())
			.thisYearSettledForSellerTax(platformSettlementHistoryDTO.getThisYearSettledForSellerTax())
			.thisYearEarned(platformSettlementHistoryDTO.getThisYearEarned())
			.build();
	}
}