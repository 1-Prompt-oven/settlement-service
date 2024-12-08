package com.promptoven.settlementservice.adaptor.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.promptoven.settlementservice.adaptor.jpa.entity.SoldProductLedgerEntity;

public interface SoldProductLedgerRepository extends JpaRepository<SoldProductLedgerEntity, Long> {

	@Query("select s from SoldProductLedgerEntity s where s.sellerUUID = ?1 and s.settled = ?2")
	List<SoldProductLedgerEntity> findBySellerUUIDAndSettled(String sellerUUID, boolean settled);

	@Query("select s from SoldProductLedgerEntity s where s.sellerUUID = ?1 and s.soldAt between ?2 and ?3")
	List<SoldProductLedgerEntity> findBySellerUUIDAndSoldAtIsBetween(String sellerUUID, LocalDate begin, LocalDate end);
}