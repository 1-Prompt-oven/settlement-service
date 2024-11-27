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

import com.promptoven.settlementservice.adaptor.web.controller.mapper.CreateSettlementProfileRequestMapper;
import com.promptoven.settlementservice.adaptor.web.controller.mapper.SettlementProfileResponseMapper;
import com.promptoven.settlementservice.adaptor.web.controller.mapper.UpdateAccountRequestMapper;
import com.promptoven.settlementservice.adaptor.web.controller.mapper.UpdateAddressRequestMapper;
import com.promptoven.settlementservice.adaptor.web.controller.mapper.UpdatePhoneRequestMapper;
import com.promptoven.settlementservice.adaptor.web.controller.mapper.UpdateTaxIDRequestMapper;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.CreateSettlementProfileRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateAccountRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateAddressRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdatePhoneRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.in.UpdateTaxIDRequestVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.out.SettlementProfileFullResponseVO;
import com.promptoven.settlementservice.adaptor.web.controller.vo.out.SettlementProfileResponseVO;
import com.promptoven.settlementservice.application.port.in.usecase.SettlementProfileUseCase;
import com.promptoven.settlementservice.adaptor.web.util.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class SettlementRestController {

	private final SettlementProfileUseCase settlementProfileUseCase;

	@PostMapping("/member/settlement/profile")
	public BaseResponse<Void> createSettlementProfile(
		@RequestBody CreateSettlementProfileRequestVO createSettlementProfileRequestVO) {
		settlementProfileUseCase.createSettlementProfile(
			CreateSettlementProfileRequestMapper.toDTO(createSettlementProfileRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/seller/settlement/profile/account")
	public BaseResponse<Void> updateAccount(@RequestBody UpdateAccountRequestVO updateAccountRequestVO) {
		settlementProfileUseCase.updateAccount(UpdateAccountRequestMapper.toDTO(updateAccountRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/seller/settlement/profile/address")
	public BaseResponse<Void> updateAddress(@RequestBody UpdateAddressRequestVO updateAddressRequestVO) {
		settlementProfileUseCase.updateAddress(UpdateAddressRequestMapper.toDTO(updateAddressRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/seller/settlement/profile/phone")
	public BaseResponse<Void> updatePhone(@RequestBody UpdatePhoneRequestVO updatePhoneRequestVO) {
		settlementProfileUseCase.updatePhone(UpdatePhoneRequestMapper.toDTO(updatePhoneRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/seller/settlement/profile/taxID")
	public BaseResponse<Void> updateTaxID(@RequestBody UpdateTaxIDRequestVO updateTaxIDRequestVO) {
		settlementProfileUseCase.updateTaxID(UpdateTaxIDRequestMapper.toDTO(updateTaxIDRequestVO));
		return new BaseResponse<>();
	}

	@GetMapping("/seller/settlement/profile/list/{memberUUID}")
	public BaseResponse<List<SettlementProfileResponseVO>> getMySettlementProfiles(@PathVariable String memberUUID) {
		List<SettlementProfileResponseVO> profiles = settlementProfileUseCase.getMySettlementProfiles(memberUUID)
			.stream()
			.map(SettlementProfileResponseMapper::toResponseVO)
			.collect(Collectors.toList());
		return new BaseResponse<>(profiles);
	}

	@GetMapping("/seller/settlement/profile/{profileUUID}")
	public BaseResponse<SettlementProfileFullResponseVO> getSettlementProfileFullInfo(@PathVariable String profileUUID) {
		SettlementProfileFullResponseVO profile = SettlementProfileResponseMapper.toFullResponseVO(
			settlementProfileUseCase.getSettlementProfileFullInfo(profileUUID));
		return new BaseResponse<>(profile);
	}
}
