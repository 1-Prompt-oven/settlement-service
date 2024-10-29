package com.promptoven.settlementservice.adaptor.web.controller.vo.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressRequestVO {

	private String profileID;
	private String postcode;
	private String address;

}
