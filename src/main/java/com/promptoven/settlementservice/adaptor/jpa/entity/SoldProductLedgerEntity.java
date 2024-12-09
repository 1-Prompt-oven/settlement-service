package com.promptoven.settlementservice.adaptor.jpa.entity;

import java.time.LocalDateTime;

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
	@Index(name = "idx_sellerUUID", columnList = "sellerUUID")})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SoldProductLedgerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sellerUUID;
	private String productName;
	private Long price;
	private LocalDateTime soldAt;
	private boolean settled;
	private boolean suspended;
}
