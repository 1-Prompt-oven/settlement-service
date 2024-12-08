package com.promptoven.settlementservice.adaptor.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promptoven.settlementservice.adaptor.jpa.entity.PlatformSettlementHistoryEntity;

public interface PlatformSettlementHistoryRepository extends JpaRepository<PlatformSettlementHistoryEntity, Long> {
}