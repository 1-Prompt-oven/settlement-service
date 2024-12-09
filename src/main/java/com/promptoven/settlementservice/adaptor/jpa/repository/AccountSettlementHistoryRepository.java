package com.promptoven.settlementservice.adaptor.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.promptoven.settlementservice.adaptor.jpa.entity.AccountSettlementHistoryEntity;

public interface AccountSettlementHistoryRepository extends JpaRepository<AccountSettlementHistoryEntity, Long> {

	@Query("select a from AccountSettlementHistoryEntity a where a.recordedAt = ?1")
	List<AccountSettlementHistoryEntity> findByRecordedAt(LocalDate recordedAt);

	@Query("select a from AccountSettlementHistoryEntity a where a.sellerUUID = ?1 and a.recordedAt between ?2 and ?3")
	List<AccountSettlementHistoryEntity>
	findBySellerUUIDAndRecordedAtBetween(String sellerUUID, LocalDate begin, LocalDate end);
}