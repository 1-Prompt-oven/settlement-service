package com.promptoven.settlementservice.domain;

import com.promptoven.settlementservice.domain.dto.AddressModelDTO;
import com.promptoven.settlementservice.domain.dto.BankAccountModelDTO;
import com.promptoven.settlementservice.domain.dto.SettlementProfileModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SettlementProfile {

	private String memberID;
	private String settlementProfileID;
	// Tax ID must be Encrypted
	private String taxID;
	private String accountID;
	private String bankName;
	private String phone;
	private String postcode;
	private String address;

	public static SettlementProfile createSettlementProfile(SettlementProfileModelDTO settlementProfileModelDTO) {
		return SettlementProfile.builder()
			.memberID(settlementProfileModelDTO.getMemberID())
			.settlementProfileID(settlementProfileModelDTO.getSettlementProfileID())
			.taxID(settlementProfileModelDTO.getTaxID())
			.accountID(settlementProfileModelDTO.getAccountID())
			.bankName(settlementProfileModelDTO.getBankName())
			.phone(settlementProfileModelDTO.getPhone())
			.postcode(settlementProfileModelDTO.getPostcode())
			.address(settlementProfileModelDTO.getAddress())
			.build();
	}

	public static SettlementProfile updateTaxID(SettlementProfile oldProfile, String taxID) {
		return SettlementProfile.builder()
			.memberID(oldProfile.getMemberID())
			.settlementProfileID(oldProfile.getSettlementProfileID())
			.taxID(taxID)
			.accountID(oldProfile.getAccountID())
			.bankName(oldProfile.getBankName())
			.phone(oldProfile.getPhone())
			.postcode(oldProfile.getPostcode())
			.address(oldProfile.getAddress())
			.build();
	}

	public static SettlementProfile updateAccount(SettlementProfile oldProfile,
		BankAccountModelDTO bankAccountModelDTO) {
		return SettlementProfile.builder()
			.memberID(oldProfile.getMemberID())
			.settlementProfileID(oldProfile.getSettlementProfileID())
			.taxID(oldProfile.getTaxID())
			.accountID(bankAccountModelDTO.getAccountID())
			.bankName(bankAccountModelDTO.getBankName())
			.phone(oldProfile.getPhone())
			.postcode(oldProfile.getPostcode())
			.address(oldProfile.getAddress())
			.build();
	}

	public static SettlementProfile updatePhone(SettlementProfile oldProfile, String phone) {
		return SettlementProfile.builder()
			.memberID(oldProfile.getMemberID())
			.settlementProfileID(oldProfile.getSettlementProfileID())
			.taxID(oldProfile.getTaxID())
			.accountID(oldProfile.getAccountID())
			.bankName(oldProfile.getBankName())
			.phone(phone)
			.postcode(oldProfile.getPostcode())
			.address(oldProfile.getAddress())
			.build();
	}

	public static SettlementProfile updateAddress(SettlementProfile oldProfile, AddressModelDTO addressModelDTO) {
		return SettlementProfile.builder()
			.memberID(oldProfile.getMemberID())
			.settlementProfileID(oldProfile.getSettlementProfileID())
			.taxID(oldProfile.getTaxID())
			.accountID(oldProfile.getAccountID())
			.bankName(oldProfile.getBankName())
			.phone(oldProfile.getPhone())
			.postcode(addressModelDTO.getPostcode())
			.address(addressModelDTO.getAddress())
			.build();
	}

}
