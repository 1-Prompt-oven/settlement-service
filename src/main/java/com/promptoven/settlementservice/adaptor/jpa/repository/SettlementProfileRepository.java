package com.promptoven.settlementservice.adaptor.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.promptoven.settlementservice.adaptor.jpa.entity.SettlementProfileEntity;

public interface SettlementProfileRepository extends JpaRepository<SettlementProfileEntity, Long> {

	List<SettlementProfileEntity> findByMemberID(String memberID);

	SettlementProfileEntity findBySettlementProfileID(String profileID);

	@Query("select distinct s.memberID from SettlementProfileEntity s")
	List<String> findAllSellerUUID();

	@Query("select s.taxID from SettlementProfileEntity s where s.memberID = ?1 limit 1")
	String findFirstTaxIDByMemberID(String sellerUUID);
}
