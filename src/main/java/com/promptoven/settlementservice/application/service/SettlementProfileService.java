package com.promptoven.settlementservice.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.application.port.in.dto.CreateSettlementProfileRequestDTO;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementProfileUseCase;
import com.promptoven.settlementservice.application.port.out.call.EventPublisher;
import com.promptoven.settlementservice.application.port.out.call.SettlementProfilePersistence;
import com.promptoven.settlementservice.application.port.out.dto.SettlementFirstCreateEvent;
import com.promptoven.settlementservice.application.port.out.dto.SettlementProfileDTO;
import com.promptoven.settlementservice.application.util.Encrypter;
import com.promptoven.settlementservice.domain.SettlementProfile;
import com.promptoven.settlementservice.domain.dto.AddressModelDTO;
import com.promptoven.settlementservice.domain.dto.BankAccountModelDTO;
import com.promptoven.settlementservice.domain.dto.SettlementProfileModelDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SettlementProfileService implements SettlementProfileUseCase {

	private final SettlementProfilePersistence settlementProfilePersistence;
	private final Encrypter encrypter;
	private final EventPublisher eventPublisher;

	private String encryptTaxID(String rawTaxID) {
		try {
			return encrypter.encrypt(rawTaxID);
		} catch (Exception e) {
			throw new RuntimeException("Failed to running Text Encrypter");
		}
	}

	@Override
	public void createSettlementProfile(CreateSettlementProfileRequestDTO createSettlementProfileRequestDTO) {

		String memberID = createSettlementProfileRequestDTO.getMemberID();
		Integer count = settlementProfilePersistence.getAll(memberID).size();
		String settlementProfileID = UUID.randomUUID().toString();
		// 중복 UUID 생성 방지 위해서 while loop 도입
		while (null != settlementProfilePersistence.getByProfileID(settlementProfileID)) {
			settlementProfileID = UUID.randomUUID().toString();
		}
		SettlementProfileModelDTO settlementProfileModelDTO = SettlementProfileModelDTO.builder()
			.memberID(memberID)
			.settlementProfileID(settlementProfileID)
			.taxID(encryptTaxID(createSettlementProfileRequestDTO.getTaxID()))
			.accountID(createSettlementProfileRequestDTO.getAccountID())
			.bankName(createSettlementProfileRequestDTO.getBankName())
			.phone(createSettlementProfileRequestDTO.getPhone())
			.postcode(createSettlementProfileRequestDTO.getPostcode())
			.address(createSettlementProfileRequestDTO.getAddress())
			.build();

		SettlementProfile createdSettlementProfile = SettlementProfile.createSettlementProfile(
			settlementProfileModelDTO);

		settlementProfilePersistence.create(createdSettlementProfile);
		if (count.equals(0)) {
			SettlementFirstCreateEvent settlementFirstCreteEvent = SettlementFirstCreateEvent.builder()
				.memberUUID(memberID)
				.build();
			eventPublisher.publish("${settlement-first-create-event}", settlementFirstCreteEvent);
		}
	}

	@Override
	public void updateAccount(String profileID, String accountID, String bankName) {

		SettlementProfile oldSettlementProfile
			= settlementProfilePersistence.getByProfileID(profileID);

		BankAccountModelDTO bankAccountModelDTO = BankAccountModelDTO.builder()
			.accountID(accountID)
			.bankName(bankName)
			.build();

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateAccount(oldSettlementProfile, bankAccountModelDTO);

		settlementProfilePersistence.updateSettlementProfile(updatedSettlementProfile);
	}

	@Override
	public void updateTaxID(String profileID, String taxID) {

		SettlementProfile oldSettlementProfile
			= settlementProfilePersistence.getByProfileID(profileID);

		String encryptedTaxID = encryptTaxID(taxID);

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateTaxID(oldSettlementProfile, encryptedTaxID);

		settlementProfilePersistence.updateSettlementProfile(updatedSettlementProfile);
	}

	@Override
	public List<SettlementProfileDTO> getMySettlementProfiles(String memberUUID) {
		return settlementProfilePersistence.getAll(memberUUID);
	}

	@Override
	public SettlementProfileDTO getSettlementProfileFullInfo(String profileUUID) {
		SettlementProfile targetSettlementProfile
			= settlementProfilePersistence.getByProfileID(profileUUID);
		String decryptedTaxID = DecryptTaxID(targetSettlementProfile.getTaxID());

		return SettlementProfileDTO.builder()
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
			= settlementProfilePersistence.getByProfileID(profileID);

		SettlementProfile updatedSettlementProfile = SettlementProfile.updatePhone(oldSettlementProfile, phone);

		settlementProfilePersistence.updateSettlementProfile(updatedSettlementProfile);
	}

	@Override
	public void updateAddress(String profileID, String postcode, String address) {
		SettlementProfile oldSettlementProfile
			= settlementProfilePersistence.getByProfileID(profileID);

		AddressModelDTO addressModelDTO = AddressModelDTO.builder()
			.postcode(postcode)
			.address(address)
			.build();

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateAddress(oldSettlementProfile, addressModelDTO);

		settlementProfilePersistence.updateSettlementProfile(updatedSettlementProfile);
	}

}
