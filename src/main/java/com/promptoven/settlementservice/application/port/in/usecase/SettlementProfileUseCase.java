package com.promptoven.settlementservice.application.port.in.usecase;

import java.util.List;

import com.promptoven.settlementservice.application.port.in.dto.CreateSettlementProfileRequestDTO;
import com.promptoven.settlementservice.application.port.out.dto.SettlementProfileDTO;

public interface SettlementProfileUseCase {

	void createSettlementProfile(CreateSettlementProfileRequestDTO createSettlementProfileRequestDTO);

	void updateAccount(String ProfileID, String accountID, String bankName);

	void updateAddress(String ProfileID, String postcode, String address);

	void updatePhone(String ProfileID, String Phone);

	void updateTaxID(String ProfileID, String TaxID);

	List<SettlementProfileDTO> getMySettlementProfiles(String memberUUID);

	SettlementProfileDTO getSettlementProfileFullInfo(String profileUUID);
}
