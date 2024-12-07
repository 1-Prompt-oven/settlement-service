package com.promptoven.settlementservice.adaptor.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promptoven.settlementservice.adaptor.jpa.entity.SoldProductLedgerEntity;

public interface SoldProductLedgerRepository extends JpaRepository<SoldProductLedgerEntity, Long> {
}