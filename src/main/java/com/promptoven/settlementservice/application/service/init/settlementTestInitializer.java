package com.promptoven.settlementservice.application.service.init;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;

import com.promptoven.settlementservice.application.port.out.call.LedgerPersistence;
import com.promptoven.settlementservice.application.port.out.call.SettlementProfilePersistence;
import com.promptoven.settlementservice.application.service.SettlementAggregateService;
import com.promptoven.settlementservice.application.service.dto.SoldProductLedgerDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class settlementTestInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final SettlementAggregateService settlementAggregateService;
	private final LedgerPersistence ledgerPersistence;
	private final SettlementProfilePersistence settlementProfilePersistence;

	private final List<LocalDate> dates = LocalDate.of(2023, 12, 1)
		.datesUntil(LocalDate.now())
		.toList();

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		if (!ledgerPersistence.get(Pair.of(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 1)), "test").isEmpty()) {
			return;
		}

		ledgerPersistence.record(SoldProductLedgerDTO.builder()
			.sellerUUID("test")
			.productName("testValueInserted")
			.price(0L)
			.soldAt(LocalDateTime.of(2000, 1, 1, 3, 3, 3))
			.settled(true)
			.orderID("testInitial")
			.build());

		List<String> testSeller = settlementProfilePersistence.getSellerUUIDs();
		Random random = new Random();
		long minPrice = 1000L;
		long maxPrice = 5000L;
		for (LocalDate date : dates) {
			for (String seller : testSeller) {
				for (int i = 0; i < 3; i++) {
					ledgerPersistence.record(SoldProductLedgerDTO.builder()
						.sellerUUID(seller)
						.productName("testValue")
						.price(minPrice + (long)(random.nextDouble() * (maxPrice - minPrice)))
						.soldAt(date.atStartOfDay().plusHours(2).plusMinutes(i))
						.settled(false)
						.orderID("testInitial" + i)
						.build());
				}
			}
			settlementAggregateService.settleLedger(date);
		}
	}
}
