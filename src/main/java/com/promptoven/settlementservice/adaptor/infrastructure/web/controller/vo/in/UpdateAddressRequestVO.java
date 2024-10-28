package com.promptoven.settlementservice.adaptor.infrastructure.web.controller.vo.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressRequestVO {

    private String profileID;
    private String postcode;
    private String address;

}
