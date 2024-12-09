package com.promptoven.settlementservice.adaptor.web.controller.vo.in;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAppendRequestVO {
	private String sellerUUID;
	private String productName;
	private Long price;
	private LocalDateTime soldAt;
	private String orderID;
}
