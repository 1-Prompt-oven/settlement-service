package com.promptoven.settlementservice.adaptor.infrastructure.web.controller.vo.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaxIDRequestVO {

    private String profileID;
    private String taxID;

}
