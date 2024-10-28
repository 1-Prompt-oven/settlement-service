package com.promptoven.settlementservice.domain;

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

	public static SettlementProfile createSettlementProfile(String memberID, String settlementProfileID, String taxID,
		String accountID, String bankName, String phone, String postcode, String address) {
		return SettlementProfile.builder()
			.memberID(memberID)
			.settlementProfileID(settlementProfileID)
			.taxID(taxID)
			.accountID(accountID)
			.bankName(bankName)
			.phone(phone)
			.postcode(postcode)
			.address(address)
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

	public static SettlementProfile updateAccount(SettlementProfile oldProfile, String accountID, String bankName) {
		return SettlementProfile.builder()
			.memberID(oldProfile.getMemberID())
			.settlementProfileID(oldProfile.getSettlementProfileID())
			.taxID(oldProfile.getTaxID())
			.accountID(accountID)
			.bankName(bankName)
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

	public static SettlementProfile updateAddress(SettlementProfile oldProfile, String postcode, String address) {
		return SettlementProfile.builder()
			.memberID(oldProfile.getMemberID())
			.settlementProfileID(oldProfile.getSettlementProfileID())
			.taxID(oldProfile.getTaxID())
			.accountID(oldProfile.getAccountID())
			.bankName(oldProfile.getBankName())
			.phone(oldProfile.getPhone())
			.postcode(postcode)
			.address(address)
			.build();
	}

}
