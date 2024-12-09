package com.promptoven.settlementservice.adaptor.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.promptoven.settlementservice.adaptor.jpa.entity.SoldProductLedgerEntity;

public interface SoldProductLedgerRepository extends JpaRepository<SoldProductLedgerEntity, Long> {

	@Query("select s from SoldProductLedgerEntity s where s.sellerUUID = ?1 and s.settled = ?2 and s.suspended = false")
	List<SoldProductLedgerEntity> findBySellerUUIDAndSettled(String sellerUUID, boolean settled);

	@Query("select s from SoldProductLedgerEntity s where s.sellerUUID = ?1 and s.soldAt between ?2 and ?3")
	List<SoldProductLedgerEntity> findBySellerUUIDAndSoldAtIsBetween(String sellerUUID, LocalDate begin, LocalDate end);

	@Modifying
	@Query("update SoldProductLedgerEntity s set s.settled = true where s.orderID = ?4 and s.settled = false and s.sellerUUID = ?1 and s.productName = ?2 and s.price = ?3")
	void settle(String sellerUUID, String productName, Long price, String orderID);

	@Modifying
	@Query("update SoldProductLedgerEntity s set s.suspended = false where s.orderID = ?4 and s.settled = false and s.sellerUUID = ?1 and s.productName = ?2 and s.price = ?3")
	void unsuspend(String sellerUUID, String productName, Long price, String orderID);
}