package com.promptoven.settlementservice.adaptor.web.controller.vo.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountRequestVO {

	private String profileID;
	private String accountID;
	private String bankName;

}
