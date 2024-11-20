package com.promptoven.settlementservice.adaptor.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.promptoven.settlementservice.adaptor.jpa.entity.SettlementProfileEntity;

public interface SettlementProfileRepository extends JpaRepository<SettlementProfileEntity, Long> {

    List<SettlementProfileEntity> findByMemberID(String memberID);
    SettlementProfileEntity findBySettlementProfileID(String profileID);

}
