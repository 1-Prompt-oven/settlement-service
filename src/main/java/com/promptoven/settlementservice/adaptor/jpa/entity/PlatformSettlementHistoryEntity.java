package com.promptoven.settlementservice.adaptor.jpa.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = {
	@Index(name = "idx_recordedAt", columnList = "recordedAt")})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlatformSettlementHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate recordedAt;
	private Long accumulatedSold;
	private Long accumulatedEarned;
	private Long accumulatedSettledForSellerTax;
	private Long accumulatedSettledForAdminTax;
	private Long thisYearSettledForAdminTax;
	private Long thisYearSettledForSellerTax;
	private Long thisYearEarned;
}