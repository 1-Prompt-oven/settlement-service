package com.promptoven.settlementservice.adaptor.infrastructure.web.controller.vo.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountRequestVO {

    private String profileID;
    private String accountID;
    private String bankName;

}
