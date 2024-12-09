package com.promptoven.settlementservice.application.port.out.call;

import com.promptoven.settlementservice.application.service.dto.PlatformSettlementHistoryDTO;

public interface MailSending {

	void sendMail(PlatformSettlementHistoryDTO platformSettlementHistoryDTO);

}
