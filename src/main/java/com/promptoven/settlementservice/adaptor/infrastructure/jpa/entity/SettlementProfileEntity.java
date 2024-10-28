package com.promptoven.settlementservice.adaptor.infrastructure.jpa.entity;

import com.promptoven.settlementservice.domain.SettlementProfile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "settlement_profile")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SettlementProfileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String settlementProfileID;
	private String memberID;
	private String taxID;
	private String accountID;
	private String bankName;
	private String phone;
	private String postcode;
	private String address;

    public static SettlementProfileEntity fromDomain(SettlementProfile settlementProfile) {
        return SettlementProfileEntity.builder()
            .settlementProfileID(settlementProfile.getSettlementProfileID())
            .memberID(settlementProfile.getMemberID())
            .taxID(settlementProfile.getTaxID())
            .accountID(settlementProfile.getAccountID())
            .bankName(settlementProfile.getBankName())
            .phone(settlementProfile.getPhone())
            .postcode(settlementProfile.getPostcode())
            .address(settlementProfile.getAddress())
            .build();
    }

    public void SetID(Long ID){
        this.id=ID;
    }

    public SettlementProfile toDomain() {
        return SettlementProfile.builder()
            .settlementProfileID(settlementProfileID)
            .memberID(memberID)
            .taxID(taxID)
            .accountID(accountID)
            .bankName(bankName)
            .phone(phone)
            .postcode(postcode)
            .address(address)
            .build();
    }
}
