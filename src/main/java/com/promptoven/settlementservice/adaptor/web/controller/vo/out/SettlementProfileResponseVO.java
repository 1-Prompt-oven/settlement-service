package com.promptoven.settlementservice.adaptor.web.controller.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SettlementProfileResponseVO {

	private String memberID;
	private String settlementProfileID;
	private String accountID;
	private String bankName;
	private String phone;
	private String postcode;
	private String address;

}
