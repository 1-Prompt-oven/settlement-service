package com.promptoven.settlementservice.adaptor.jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.settlementservice.adaptor.jpa.entity.SoldProductLedgerEntity;
import com.promptoven.settlementservice.adaptor.jpa.repository.SoldProductLedgerRepository;
import com.promptoven.settlementservice.application.port.out.call.LedgerPersistence;
import com.promptoven.settlementservice.application.service.dto.SoldProductLedgerDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LedgerPersistenceByJpa implements LedgerPersistence {

	private final SoldProductLedgerRepository soldProductLedgerRepository;

	@Override
	public void record(SoldProductLedgerDTO soldProductLedgerDTO) {
		soldProductLedgerRepository.save(DtoEntityMapper.toEntity(soldProductLedgerDTO));
	}

	@Override
	public void recordSuspend(SoldProductLedgerDTO soldProductLedgerDTO) {
		soldProductLedgerRepository.save(DtoEntityMapper.toSuspendedEntity(soldProductLedgerDTO));
	}

	@Override
	public List<SoldProductLedgerDTO> get(Pair<LocalDate, LocalDate> range, String targetUUID) {
		LocalDateTime beginDate = range.getFirst().atStartOfDay();
		LocalDateTime endDate = range.getSecond().plusDays(1).atStartOfDay();
		return soldProductLedgerRepository.findBySellerUUIDAndSoldAtIsBetween(targetUUID, beginDate, endDate).stream()
			.map(DtoEntityMapper::toDTO)
			.toList();
	}

	@Override
	public List<SoldProductLedgerDTO> getUnsettled(String targetUUID) {
		return soldProductLedgerRepository.findBySellerUUIDAndSettled(targetUUID, false).stream()
			.map(DtoEntityMapper::toDTO)
			.toList();
	}

	@Override
	@Transactional
	public void markSettle(SoldProductLedgerDTO soldProductLedgerDTO) {
		SoldProductLedgerEntity soldProductLedgerEntity = DtoEntityMapper.toEntity(soldProductLedgerDTO);
		String sellerUUID = soldProductLedgerEntity.getSellerUUID();
		String productName = soldProductLedgerEntity.getProductName();
		Long price = soldProductLedgerEntity.getPrice();
		String orderID = soldProductLedgerEntity.getOrderID();
		soldProductLedgerRepository.settle(sellerUUID, productName, price, orderID);
	}

	@Override
	public void markUnSuspend(SoldProductLedgerDTO soldProductLedgerDTO) {
		SoldProductLedgerEntity soldProductLedgerEntity = DtoEntityMapper.toEntity(soldProductLedgerDTO);
		String sellerUUID = soldProductLedgerEntity.getSellerUUID();
		String productName = soldProductLedgerEntity.getProductName();
		Long price = soldProductLedgerEntity.getPrice();
		String orderID = soldProductLedgerEntity.getOrderID();
		soldProductLedgerRepository.unsuspend(sellerUUID, productName, price, orderID);
	}

}

class DtoEntityMapper {
	public static SoldProductLedgerDTO toDTO(SoldProductLedgerEntity soldProductLedgerEntity) {
		return SoldProductLedgerDTO.builder()
			.sellerUUID(soldProductLedgerEntity.getSellerUUID())
			.productName(soldProductLedgerEntity.getProductName())
			.price(soldProductLedgerEntity.getPrice())
			.soldAt(soldProductLedgerEntity.getSoldAt())
			.orderID(soldProductLedgerEntity.getOrderID())
			.settled(soldProductLedgerEntity.isSettled())
			.build();
	}

	public static SoldProductLedgerEntity toEntity(SoldProductLedgerDTO soldProductLedgerDTO) {
		return SoldProductLedgerEntity.builder()
			.sellerUUID(soldProductLedgerDTO.getSellerUUID())
			.productName(soldProductLedgerDTO.getProductName())
			.price(soldProductLedgerDTO.getPrice())
			.soldAt(soldProductLedgerDTO.getSoldAt())
			.orderID(soldProductLedgerDTO.getOrderID())
			.settled(soldProductLedgerDTO.isSettled())
			.suspended(false)
			.build();
	}

	public static SoldProductLedgerEntity toSuspendedEntity(SoldProductLedgerDTO soldProductLedgerDTO) {
		return SoldProductLedgerEntity.builder()
			.sellerUUID(soldProductLedgerDTO.getSellerUUID())
			.productName(soldProductLedgerDTO.getProductName())
			.price(soldProductLedgerDTO.getPrice())
			.soldAt(soldProductLedgerDTO.getSoldAt())
			.orderID(soldProductLedgerDTO.getOrderID())
			.settled(soldProductLedgerDTO.isSettled())
			.suspended(true)
			.build();
	}
}