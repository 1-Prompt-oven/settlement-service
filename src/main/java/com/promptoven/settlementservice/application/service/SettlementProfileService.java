package com.promptoven.settlementservice.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.application.port.in.dto.CreateSettlementProfileRequestDTO;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementProfileUseCase;
import com.promptoven.settlementservice.application.port.out.call.SettlementProfilePersistence;
import com.promptoven.settlementservice.application.port.out.dto.SettlementProfileFullInfoDTO;
import com.promptoven.settlementservice.application.util.Encrypter;
import com.promptoven.settlementservice.domain.SettlementProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SettlementProfileService implements SettlementProfileUseCase {

	private final SettlementProfilePersistence settlementProfilePersistence;
	private final Encrypter encrypter;

	private String encryptTaxID(String rawTaxID) {
		try {
			return encrypter.encrypt(rawTaxID);
		} catch (Exception e) {
			throw new RuntimeException("Failed to running Text Encrypter");
		}
	}

	@Override
	public void createSettlementProfile(CreateSettlementProfileRequestDTO createSettlementProfileRequestDTO) {

		String settlementProfileID = UUID.randomUUID().toString();
		// 중복 UUID 생성 방지 위해서 while loop 도입
		while (null == settlementProfilePersistence.getSettlementProfileByProfileID(settlementProfileID)) {
			settlementProfileID = UUID.randomUUID().toString();
		}

		SettlementProfile createdSettlementProfile = SettlementProfile.createSettlementProfile(
			createSettlementProfileRequestDTO.getMemberID(), settlementProfileID,
			encryptTaxID(createSettlementProfileRequestDTO.getTaxID()),
			createSettlementProfileRequestDTO.getAccountID(), createSettlementProfileRequestDTO.getBankName(),
			createSettlementProfileRequestDTO.getPhone(), createSettlementProfileRequestDTO.getPostcode(),
			createSettlementProfileRequestDTO.getAddress());

		settlementProfilePersistence.createSettlementProfile(createdSettlementProfile);
	}

	@Override
	public void updateAccount(String profileID, String accountID, String bankName) {

		SettlementProfile oldSettlementProfile
			= settlementProfilePersistence.getSettlementProfileByProfileID(profileID);

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateAccount(oldSettlementProfile, accountID, bankName);

		settlementProfilePersistence.updateSettlementProfile(updatedSettlementProfile);
	}

	@Override
	public void updateTaxID(String profileID, String taxID) {

		SettlementProfile oldSettlementProfile
			= settlementProfilePersistence.getSettlementProfileByProfileID(profileID);

		String encryptedTaxID = encryptTaxID(taxID);

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateTaxID(oldSettlementProfile, encryptedTaxID);

		settlementProfilePersistence.updateSettlementProfile(updatedSettlementProfile);
	}

	@Override
	public List<SettlementProfile> getMySettlementProfiles(String memberUUID) {
		return settlementProfilePersistence.getAllSettlementProfile(memberUUID);
	}

	@Override
	public SettlementProfileFullInfoDTO getSettlementProfileFullInfo(String profileUUID) {
		SettlementProfile targetSettlementProfile
			= settlementProfilePersistence.getSettlementProfileByProfileID(profileUUID);
		String decryptedTaxID = DecryptTaxID(targetSettlementProfile.getTaxID());

		return SettlementProfileFullInfoDTO.builder()
			.memberID(targetSettlementProfile.getMemberID())
			.settlementProfileID(profileUUID)
			.taxID(decryptedTaxID)
			.accountID(targetSettlementProfile.getAccountID())
			.bankName(targetSettlementProfile.getBankName())
			.phone(targetSettlementProfile.getPhone())
			.postcode(targetSettlementProfile.getPostcode())
			.address(targetSettlementProfile.getAddress())
			.build();
	}

	private String DecryptTaxID(String encryptedTaxID) {
		try {
			return encrypter.decrypt(encryptedTaxID);
		} catch (Exception e) {
			throw new RuntimeException("Failed to running Text Decrypter");
		}
	}

	@Override
	public void updatePhone(String profileID, String phone) {

		SettlementProfile oldSettlementProfile
			= settlementProfilePersistence.getSettlementProfileByProfileID(profileID);

		SettlementProfile updatedSettlementProfile = SettlementProfile.updatePhone(oldSettlementProfile, phone);

		settlementProfilePersistence.updateSettlementProfile(updatedSettlementProfile);
	}

	@Override
	public void updateAddress(String profileID, String postcode, String address) {
		SettlementProfile oldSettlementProfile
			= settlementProfilePersistence.getSettlementProfileByProfileID(profileID);

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateAddress(oldSettlementProfile, postcode, address);

		settlementProfilePersistence.updateSettlementProfile(updatedSettlementProfile);
	}

}
