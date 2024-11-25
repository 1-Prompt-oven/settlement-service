package com.promptoven.settlementservice.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.promptoven.settlementservice.application.port.in.dto.CreateSettlementProfileRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateAccountRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateAddressRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.UpdatePhoneRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateTaxIDRequestDTO;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementProfileUseCase;
import com.promptoven.settlementservice.application.port.out.call.EventPublisher;
import com.promptoven.settlementservice.application.port.out.call.SettlementProfilePersistence;
import com.promptoven.settlementservice.application.port.out.dto.SettlementFirstCreateEvent;
import com.promptoven.settlementservice.application.service.dto.SettlementProfileDTO;
import com.promptoven.settlementservice.application.service.dto.SettlementProfileDomainDTOMapper;
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
	private final SettlementProfileDomainDTOMapper settlementProfileDomainDTOMapper;
	@Value("${settlement-first-create-event}")
	private String settlementFirstCreateEventTopic;

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

		settlementProfilePersistence.create(settlementProfileDomainDTOMapper.toDTO(createdSettlementProfile));
		if (count.equals(0)) {
			SettlementFirstCreateEvent settlementFirstCreteEvent = SettlementFirstCreateEvent.builder()
				.memberUUID(memberID)
				.build();
			eventPublisher.publish(settlementFirstCreateEventTopic, settlementFirstCreteEvent);
		}
	}

	@Override
	public void updateAccount(UpdateAccountRequestDTO updateAccountRequestDTO) {

		SettlementProfileDTO oldSettlementProfileDTO
			= settlementProfilePersistence.getByProfileID(updateAccountRequestDTO.getProfileID());

		BankAccountModelDTO bankAccountModelDTO = BankAccountModelDTO.builder()
			.accountID(updateAccountRequestDTO.getAccountID())
			.bankName(updateAccountRequestDTO.getBankName())
			.build();

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateAccount(settlementProfileDomainDTOMapper.toDomain(oldSettlementProfileDTO),
			bankAccountModelDTO);

		settlementProfilePersistence.updateSettlementProfile(
			settlementProfileDomainDTOMapper.toDTO(updatedSettlementProfile));
	}

	@Override
	public void updateTaxID(UpdateTaxIDRequestDTO updateTaxIDRequestDTO) {

		SettlementProfileDTO oldSettlementProfileDTO
			= settlementProfilePersistence.getByProfileID(updateTaxIDRequestDTO.getProfileID());

		String encryptedTaxID = encryptTaxID(updateTaxIDRequestDTO.getTaxID());

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateTaxID(settlementProfileDomainDTOMapper.toDomain(oldSettlementProfileDTO),
			encryptedTaxID);

		settlementProfilePersistence.updateSettlementProfile(
			settlementProfileDomainDTOMapper.toDTO(updatedSettlementProfile));
	}

	@Override
	public List<SettlementProfileDTO> getMySettlementProfiles(String memberUUID) {
		return settlementProfilePersistence.getAll(memberUUID);
	}

	@Override
	public SettlementProfileDTO getSettlementProfileFullInfo(String profileUUID) {
		SettlementProfileDTO taxIDEncryptedDTO
			= settlementProfilePersistence.getByProfileID(profileUUID);
		String decryptedTaxID = DecryptTaxID(taxIDEncryptedDTO.getTaxID());

		return SettlementProfileDTO.builder()
			.memberID(taxIDEncryptedDTO.getMemberID())
			.settlementProfileID(profileUUID)
			.taxID(decryptedTaxID)
			.accountID(taxIDEncryptedDTO.getAccountID())
			.bankName(taxIDEncryptedDTO.getBankName())
			.phone(taxIDEncryptedDTO.getPhone())
			.postcode(taxIDEncryptedDTO.getPostcode())
			.address(taxIDEncryptedDTO.getAddress())
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
	public void updatePhone(UpdatePhoneRequestDTO updatePhoneRequestDTO) {

		SettlementProfileDTO oldSettlementProfileDTO
			= settlementProfilePersistence.getByProfileID(updatePhoneRequestDTO.getProfileID());

		SettlementProfile updatedSettlementProfile = SettlementProfile.updatePhone(
			settlementProfileDomainDTOMapper.toDomain(oldSettlementProfileDTO),
			updatePhoneRequestDTO.getPhone());

		settlementProfilePersistence.updateSettlementProfile(
			settlementProfileDomainDTOMapper.toDTO(updatedSettlementProfile));
	}

	@Override
	public void updateAddress(UpdateAddressRequestDTO updateAddressRequestDTO) {
		SettlementProfileDTO oldSettlementProfileDTO
			= settlementProfilePersistence.getByProfileID(updateAddressRequestDTO.getProfileID());

		AddressModelDTO addressModelDTO = AddressModelDTO.builder()
			.postcode(updateAddressRequestDTO.getPostcode())
			.address(updateAddressRequestDTO.getAddress())
			.build();

		SettlementProfile updatedSettlementProfile
			= SettlementProfile.updateAddress(settlementProfileDomainDTOMapper.toDomain(oldSettlementProfileDTO),
			addressModelDTO);

		settlementProfilePersistence.updateSettlementProfile(
			settlementProfileDomainDTOMapper.toDTO(updatedSettlementProfile));
	}

}
