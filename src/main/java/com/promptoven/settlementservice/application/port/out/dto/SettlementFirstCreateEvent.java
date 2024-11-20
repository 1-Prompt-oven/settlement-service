package com.promptoven.settlementservice.application.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SettlementFirstCreateEvent {

	private String memberUUID;

}
