package com.promptoven.settlementservice.application.port.out.call;

import java.util.List;

import com.promptoven.settlementservice.application.port.out.dto.SettlementProfileDTO;
import com.promptoven.settlementservice.domain.SettlementProfile;

public interface SettlementProfilePersistence {

	void create(SettlementProfile settlementProfile);

	void updateSettlementProfile(SettlementProfile settlementProfile);

	List<SettlementProfileDTO> getAll(String memberID);

	SettlementProfile getByProfileID(String profileID);

}
