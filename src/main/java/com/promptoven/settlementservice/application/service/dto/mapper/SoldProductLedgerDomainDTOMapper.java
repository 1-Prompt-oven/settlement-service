package com.promptoven.settlementservice.application.service.dto.mapper;

import org.springframework.stereotype.Component;

import com.promptoven.settlementservice.application.service.dto.SoldProductLedgerDTO;
import com.promptoven.settlementservice.domain.LedgerProductSelling;

@Component
public class SoldProductLedgerDomainDTOMapper {

	public SoldProductLedgerDTO toDTO(LedgerProductSelling ledgerProductSelling) {
		return SoldProductLedgerDTO.builder()
			.sellerUUID(ledgerProductSelling.getSellerUUID())
			.productName(ledgerProductSelling.getProductName())
			.price(ledgerProductSelling.getPrice())
			.soldAt(ledgerProductSelling.getSoldAt())
			.settled(ledgerProductSelling.isSettled())
			.orderID(ledgerProductSelling.getOrderID())
			.build();
	}

	public LedgerProductSelling toDomain(SoldProductLedgerDTO soldProductLedgerDTO) {
		return LedgerProductSelling.builder()
			.sellerUUID(soldProductLedgerDTO.getSellerUUID())
			.productName(soldProductLedgerDTO.getProductName())
			.price(soldProductLedgerDTO.getPrice())
			.soldAt(soldProductLedgerDTO.getSoldAt())
			.settled(soldProductLedgerDTO.isSettled())
			.orderID(soldProductLedgerDTO.getOrderID())
			.build();
	}

}
