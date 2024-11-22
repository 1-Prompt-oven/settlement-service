package com.promptoven.settlementservice.application.port.in.usecase;

import java.util.List;

import com.promptoven.settlementservice.application.port.in.dto.CreateSettlementProfileRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateAccountRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateAddressRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.UpdatePhoneRequestDTO;
import com.promptoven.settlementservice.application.port.in.dto.UpdateTaxIDRequestDTO;
import com.promptoven.settlementservice.application.service.dto.SettlementProfileDTO;

public interface SettlementProfileUseCase {

	void createSettlementProfile(CreateSettlementProfileRequestDTO createSettlementProfileRequestDTO);

	void updateAccount(UpdateAccountRequestDTO updateAccountRequestDTO);

	void updateAddress(UpdateAddressRequestDTO updateAddressRequestDTO);

	void updatePhone(UpdatePhoneRequestDTO updatePhoneRequestDTO);

	void updateTaxID(UpdateTaxIDRequestDTO updateTaxIDRequestDTO);

	List<SettlementProfileDTO> getMySettlementProfiles(String memberUUID);

	SettlementProfileDTO getSettlementProfileFullInfo(String profileUUID);
}
