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
	@Index(name = "idx_recordedAt", columnList = "recordedAt"),
	@Index(name = "idx_sellerUUID", columnList = "sellerUUID")})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountSettlementHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate recordedAt;
	private String sellerUUID;
	private Long accumulatedSold;
	private Long accumulatedEarned;
	private Long accumulatedSettled;
	private Long thisYearlyEarned;
}
