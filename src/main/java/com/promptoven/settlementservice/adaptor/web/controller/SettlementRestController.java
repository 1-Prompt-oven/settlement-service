package com.promptoven.settlementservice.adaptor.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.settlementservice.adaptor.web.controller.vo.in.CreateSettlementProfileRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateAccountRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateAddressRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdatePhoneRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateTaxIDRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.out.SettlementProfileFullResponseVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.out.SettlementProfileResponseVO;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementProfileUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class SettlementRestController {

	private final SettlementProfileUseCase settlementProfileUseCase;

	@PostMapping("/member/settlement/profile")
	public void createSettlementProfile(
		@RequestBody CreateSettlementProfileRequestVO createSettlementProfileRequestVO) {
		settlementProfileUseCase.createSettlementProfile(createSettlementProfileRequestVO.toDTO());
	}

	@PutMapping("/seller/settlement/profile/account")
	public void updateAccount(@RequestBody UpdateAccountRequestVO updateAccountRequestVO) {
		settlementProfileUseCase.updateAccount(updateAccountRequestVO.getProfileID(),
			updateAccountRequestVO.getAccountID(), updateAccountRequestVO.getBankName());
	}

	@PutMapping("/seller/settlement/profile/address")
	public void updateAddress(@RequestBody UpdateAddressRequestVO updateAddressRequestVO) {
		settlementProfileUseCase.updateAddress(updateAddressRequestVO.getProfileID(),
			updateAddressRequestVO.getPostcode(), updateAddressRequestVO.getAddress());
	}

	@PutMapping("/seller/settlement/profile/phone")
	public void updatePhone(@RequestBody UpdatePhoneRequestVO updatePhoneRequestVO) {
		settlementProfileUseCase.updatePhone(updatePhoneRequestVO.getProfileID(), updatePhoneRequestVO.getPhone());
	}

	@PutMapping("/seller/settlement/profile/taxID")
	public void updateTaxID(@RequestBody UpdateTaxIDRequestVO updateTaxIDRequestVO) {
		settlementProfileUseCase.updateTaxID(updateTaxIDRequestVO.getProfileID(), updateTaxIDRequestVO.getTaxID());
	}

	@GetMapping("/seller/settlement/profile")
	public List<SettlementProfileResponseVO> getMySettlementProfiles(@RequestBody String memberUUID) {
		return settlementProfileUseCase.getMySettlementProfiles(memberUUID).stream()
			.map(SettlementProfileResponseVO::fromDomain)
			.collect(Collectors.toList());
	}

	@GetMapping("/seller/settlement/profile/{profileUUID}")
	public SettlementProfileFullResponseVO getSettlementProfileFullInfo(@PathVariable String profileUUID) {
		return SettlementProfileFullResponseVO.fromDTO(
			settlementProfileUseCase.getSettlementProfileFullInfo(profileUUID));
	}
}
