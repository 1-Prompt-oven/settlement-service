package com.promptoven.settlementservice.adaptor.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.promptoven.settlementservice.adaptor.jpa.entity.PlatformSettlementHistoryEntity;

public interface PlatformSettlementHistoryRepository extends JpaRepository<PlatformSettlementHistoryEntity, Long> {

	@Query("select p from PlatformSettlementHistoryEntity p where p.recordedAt between ?1 and ?2")
	List<PlatformSettlementHistoryEntity>
	findByRecordedAtBetween(LocalDate begin, LocalDate end);
}