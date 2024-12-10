package com.promptoven.settlementservice.adaptor.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.settlementservice.adaptor.web.controller.mapper.LedgerAppendRequestMapper;
import com.promptoven.settlementservice.adaptor.web.controller.mapper.SettlementHistoryRequestMapper;
import com.promptoven.settlementservice.adaptor.web.controller.mapper.SettlementHistoryResponseMapper;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.LedgerAppendRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.out.AdminSettlementHistoryResponseVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.out.SettlementHistoryResponseVO;
import com.promptoven.settlementservice.adaptor.web.util.BaseResponse;
import com.promptoven.settlementservice.adaptor.web.util.BaseResponseStatus;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementAggregateUsecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2")
public class SettlementAggregateRestController {

	private final SettlementAggregateUsecase settlementAggregateUsecase;

	@PostMapping("/member/settlement/ledger")
	public BaseResponse<Void> receiveLedger(@RequestBody List<LedgerAppendRequestVO> ledgerAppendRequestVOList) {
		ledgerAppendRequestVOList.forEach(ledgerAppendRequestVO ->
			settlementAggregateUsecase.appendLedger(LedgerAppendRequestMapper.toDTO(ledgerAppendRequestVO))
		);
		return new BaseResponse<>();
	}

	@PostMapping("/member/settlement/ledgerSuspended")
	public BaseResponse<Void> receiveLedgerSuspended(
		@RequestBody List<LedgerAppendRequestVO> ledgerAppendRequestVOList) {
		ledgerAppendRequestVOList.forEach(ledgerAppendRequestVO ->
			settlementAggregateUsecase.appendSuspendedUserLedger(LedgerAppendRequestMapper.toDTO(ledgerAppendRequestVO))
		);
		return new BaseResponse<>();
	}

	@PostMapping("/member/settlement/platform/ledger")
	public BaseResponse<Void> receivePlatformLedger(
		@RequestBody List<LedgerAppendRequestVO> ledgerAppendRequestVOList) {
		ledgerAppendRequestVOList.forEach(ledgerAppendRequestVO ->
			settlementAggregateUsecase.appendPlatformLedger(LedgerAppendRequestMapper.toDTO(ledgerAppendRequestVO))
		);
		return new BaseResponse<>();
	}

	@PostMapping("/member/settlement/platform/ledgerSuspended")
	public BaseResponse<Void> receivePlatformLedgerSuspended(
		@RequestBody List<LedgerAppendRequestVO> ledgerAppendRequestVOList) {
		ledgerAppendRequestVOList.forEach(ledgerAppendRequestVO ->
			settlementAggregateUsecase.appendSuspendedPlatformLedger(
				LedgerAppendRequestMapper.toDTO(ledgerAppendRequestVO))
		);
		return new BaseResponse<>();
	}

	@PutMapping("/member/settlement/unSuspend")
	public BaseResponse<Void> unsuspend(@RequestBody List<LedgerAppendRequestVO> ledgerAppendRequestVOList) {
		ledgerAppendRequestVOList.forEach(ledgerAppendRequestVO ->
			settlementAggregateUsecase.unSuspend(LedgerAppendRequestMapper.toDTO(ledgerAppendRequestVO))
		);
		return new BaseResponse<>();
	}

	@GetMapping("/seller/settlement/history/{sellerUUID}/{beginDate}/{endDate}")
	public BaseResponse<List<SettlementHistoryResponseVO>> getHistory(@PathVariable String sellerUUID,
		@PathVariable String beginDate, @PathVariable String endDate) {
		try {
			Pair<LocalDate, LocalDate> range = queryRange(LocalDate.parse(beginDate), LocalDate.parse(endDate));
			List<SettlementHistoryResponseVO> responseVOList = settlementAggregateUsecase.getAccountHistory(
				SettlementHistoryRequestMapper.toDTO(sellerUUID, range)).stream().map(
				SettlementHistoryResponseMapper::toVO).toList();
			return new BaseResponse<>(responseVOList);
		} catch (Exception e) {
			log.error("Error while parsing date", e);
			return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/admin/settlement/history/{beginDate}/{endDate}")
	public BaseResponse<List<AdminSettlementHistoryResponseVO>> getHistory(@PathVariable String beginDate,
		@PathVariable String endDate) {
		try {
			Pair<LocalDate, LocalDate> range = queryRange(LocalDate.parse(beginDate), LocalDate.parse(endDate));
			List<AdminSettlementHistoryResponseVO> responseVOList = settlementAggregateUsecase.getAdminHistory(
				SettlementHistoryRequestMapper.toDTO("admin", range)).stream().map(
				SettlementHistoryResponseMapper::toAdminVO).toList();
			return new BaseResponse<>(responseVOList);
		} catch (Exception e) {
			log.error("Error while parsing date", e);
			return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/admin/settlement/run")
	public BaseResponse<Void> runScheduledJobTest() {
		settlementAggregateUsecase.testSchedule();
		return new BaseResponse<>();
	}

	Pair<LocalDate, LocalDate> queryRange(LocalDate begin, LocalDate end) {
		return Pair.of(begin, end);
	}
}
