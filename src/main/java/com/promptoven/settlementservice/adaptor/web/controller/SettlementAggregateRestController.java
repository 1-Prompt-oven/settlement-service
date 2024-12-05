package com.promptoven.settlementservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.settlementservice.adaptor.web.controller.vo.in.LedgerAppendRequestVO;
import com.promptoven.settlementservice.adaptor.web.util.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2")
public class SettlementAggregateRestController {

	@PostMapping("/settlement/ledger")
	public BaseResponse<Void> receiveLedger(@RequestBody LedgerAppendRequestVO ledgerAppendRequestVO) {
		return new BaseResponse<>();
	}
}
