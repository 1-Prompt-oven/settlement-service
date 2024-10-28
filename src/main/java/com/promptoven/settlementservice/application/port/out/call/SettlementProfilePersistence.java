package com.promptoven.settlementservice.application.port.out.call;

import java.util.List;
import com.promptoven.settlementservice.domain.SettlementProfile;

public interface SettlementProfilePersistence {

    void createSettlementProfile(SettlementProfile settlementProfile);
    void updateSettlementProfile(SettlementProfile settlementProfile);
    List<SettlementProfile> getAllSettlementProfile(String memberID);
    SettlementProfile getSettlementProfileByProfileID(String profileID);
    

}
