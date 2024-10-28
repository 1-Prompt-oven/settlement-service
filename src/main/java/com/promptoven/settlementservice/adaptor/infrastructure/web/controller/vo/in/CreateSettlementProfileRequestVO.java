package com.promptoven.settlementservice.adaptor.infrastructure.web.controller.vo.in;

import com.promptoven.settlementservice.application.port.in.dto.CreateSettlementProfileRequestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSettlementProfileRequestVO {

    private String memberID;
    private String taxID;
    private String accountID;
    private String bankName;
    private String phone;
    private String postcode;
    private String address;

    public CreateSettlementProfileRequestDTO toDTO() {
        return CreateSettlementProfileRequestDTO.builder()
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
