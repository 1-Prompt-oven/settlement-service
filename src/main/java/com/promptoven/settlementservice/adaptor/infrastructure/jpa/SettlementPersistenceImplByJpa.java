package com.promptoven.settlementservice.adaptor.infrastructure.jpa;

import java.util.List;
import java.util.stream.Collectors;

import com.promptoven.settlementservice.adaptor.infrastructure.jpa.entity.SettlementProfileEntity;
import com.promptoven.settlementservice.adaptor.infrastructure.jpa.repository.SettlementProfileRepository;
import com.promptoven.settlementservice.application.port.out.call.SettlementProfilePersistence;
import com.promptoven.settlementservice.domain.SettlementProfile;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SettlementPersistenceImplByJpa implements SettlementProfilePersistence {
    
    private final SettlementProfileRepository settlementProfileRepository;

    @Override
    public void createSettlementProfile(SettlementProfile settlementProfile) {
        settlementProfileRepository.save(SettlementProfileEntity.fromDomain(settlementProfile));
    }

    @Override
    public void updateSettlementProfile(SettlementProfile settlementProfile) {
        SettlementProfileEntity settlementProfileEntity = SettlementProfileEntity.fromDomain(settlementProfile);
        SettlementProfileEntity oldSettlementProfileEntity = settlementProfileRepository.findBySettlementProfileID(settlementProfile.getSettlementProfileID());
        settlementProfileEntity.SetID(oldSettlementProfileEntity.getId());
        settlementProfileRepository.save(settlementProfileEntity);
    }

    @Override
    public List<SettlementProfile> getAllSettlementProfile(String memberID) {
        return settlementProfileRepository.findByMemberID(memberID).stream()
            .map(SettlementProfileEntity::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public SettlementProfile getSettlementProfileByProfileID(String profileID) {
        return settlementProfileRepository.findBySettlementProfileID(profileID).toDomain();
    }

}
