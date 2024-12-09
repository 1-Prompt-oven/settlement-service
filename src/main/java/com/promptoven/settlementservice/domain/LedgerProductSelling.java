package com.promptoven.settlementservice.domain;

import java.time.LocalDateTime;

import com.promptoven.settlementservice.domain.dto.LedgerProductSellingModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LedgerProductSelling {
	private String sellerUUID;
	private String productName;
	private Long price;
	private LocalDateTime soldAt;
	private boolean settled;

	public static LedgerProductSelling log(LedgerProductSellingModelDTO ledgerProductSellingModelDTO) {
		return LedgerProductSelling.builder()
			.sellerUUID(ledgerProductSellingModelDTO.getSellerUUID())
			.productName(ledgerProductSellingModelDTO.getProductName())
			.price(ledgerProductSellingModelDTO.getPrice())
			.soldAt(ledgerProductSellingModelDTO.getSoldAt())
			.settled(false)
			.build();
	}

	public static LedgerProductSelling settle(LedgerProductSelling ledgerProductSelling) {
		return LedgerProductSelling.builder()
			.sellerUUID(ledgerProductSelling.getSellerUUID())
			.productName(ledgerProductSelling.getProductName())
			.price(ledgerProductSelling.getPrice())
			.soldAt(ledgerProductSelling.getSoldAt())
			.settled(true)
			.build();
	}
}
