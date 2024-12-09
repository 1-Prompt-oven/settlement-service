package com.promptoven.settlementservice.application.port.out.call;

import java.util.List;

import com.promptoven.settlementservice.application.service.dto.SettlementProfileDTO;

public interface SettlementProfilePersistence {

	void create(SettlementProfileDTO settlementProfileDTO);

	void updateSettlementProfile(SettlementProfileDTO settlementProfileDTO);

	List<SettlementProfileDTO> getAll(String memberID);

	SettlementProfileDTO getByProfileID(String profileID);

	List<String> getSellerUUIDs();

	String getTaxID(String sellerUUID);
}
