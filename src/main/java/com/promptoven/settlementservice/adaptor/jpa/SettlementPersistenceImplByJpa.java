package com.promptoven.settlementservice.adaptor.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.adaptor.jpa.entity.SettlementProfileEntity;
import com.promptoven.settlementservice.adaptor.jpa.repository.SettlementProfileRepository;
import com.promptoven.settlementservice.application.port.out.call.SettlementProfilePersistence;
import com.promptoven.settlementservice.application.service.dto.SettlementProfileDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SettlementPersistenceImplByJpa implements SettlementProfilePersistence {

	private final SettlementProfileRepository settlementProfileRepository;

	@Override
	public void create(SettlementProfileDTO settlementProfile) {
		settlementProfileRepository.save(JpaSettlementProfileDTOEntityMapper.fromDTO(settlementProfile));
	}

	@Override
	public void updateSettlementProfile(SettlementProfileDTO settlementProfile) {
		SettlementProfileEntity settlementProfileEntity =
			JpaSettlementProfileDTOEntityMapper.fromDTO(settlementProfile);
		SettlementProfileEntity oldSettlementProfileEntity = settlementProfileRepository.findBySettlementProfileID(
			settlementProfile.getSettlementProfileID());
		settlementProfileEntity.SetID(oldSettlementProfileEntity.getId());
		settlementProfileRepository.save(settlementProfileEntity);
	}

	@Override
	public List<SettlementProfileDTO> getAll(String memberID) {
		return settlementProfileRepository.findByMemberID(memberID).stream()
			.map(JpaSettlementProfileDTOEntityMapper::toDTO)
			.collect(Collectors.toList());
	}

	@Override
	public SettlementProfileDTO getByProfileID(String profileID) {
		SettlementProfileEntity foundSettlementProfileEntity = settlementProfileRepository.findBySettlementProfileID(
			profileID);
		return null != foundSettlementProfileEntity ?
			JpaSettlementProfileDTOEntityMapper.toDTO(foundSettlementProfileEntity) : null;
	}

}
