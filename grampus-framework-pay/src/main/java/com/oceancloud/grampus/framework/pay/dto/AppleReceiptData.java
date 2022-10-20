package com.oceancloud.grampus.framework.pay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 苹果票据数据实体
 *
 * @author Beck
 * @since 2022-10-20
 */
@NoArgsConstructor
@Data
public class AppleReceiptData {
	@JsonProperty("receipt")
	private ReceiptDTO receipt;
	@JsonProperty("environment")
	private String environment;
	@JsonProperty("status")
	private Integer status;

	@NoArgsConstructor
	@Data
	public static class ReceiptDTO {
		@JsonProperty("receipt_type")
		private String receiptType;
		@JsonProperty("adam_id")
		private Integer adamId;
		@JsonProperty("app_item_id")
		private Integer appItemId;
		@JsonProperty("bundle_id")
		private String bundleId;
		@JsonProperty("application_version")
		private String applicationVersion;
		@JsonProperty("download_id")
		private Long downloadId;
		@JsonProperty("version_external_identifier")
		private Integer versionExternalIdentifier;
		@JsonProperty("receipt_creation_date")
		private String receiptCreationDate;
		@JsonProperty("receipt_creation_date_ms")
		private String receiptCreationDateMs;
		@JsonProperty("receipt_creation_date_pst")
		private String receiptCreationDatePst;
		@JsonProperty("request_date")
		private String requestDate;
		@JsonProperty("request_date_ms")
		private String requestDateMs;
		@JsonProperty("request_date_pst")
		private String requestDatePst;
		@JsonProperty("original_purchase_date")
		private String originalPurchaseDate;
		@JsonProperty("original_purchase_date_ms")
		private String originalPurchaseDateMs;
		@JsonProperty("original_purchase_date_pst")
		private String originalPurchaseDatePst;
		@JsonProperty("original_application_version")
		private String originalApplicationVersion;
		@JsonProperty("in_app")
		private List<InAppDTO> inApp;

		@NoArgsConstructor
		@Data
		public static class InAppDTO {
			@JsonProperty("quantity")
			private String quantity;
			@JsonProperty("product_id")
			private String productId;
			@JsonProperty("transaction_id")
			private String transactionId;
			@JsonProperty("original_transaction_id")
			private String originalTransactionId;
			@JsonProperty("purchase_date")
			private String purchaseDate;
			@JsonProperty("purchase_date_ms")
			private String purchaseDateMs;
			@JsonProperty("purchase_date_pst")
			private String purchaseDatePst;
			@JsonProperty("original_purchase_date")
			private String originalPurchaseDate;
			@JsonProperty("original_purchase_date_ms")
			private String originalPurchaseDateMs;
			@JsonProperty("original_purchase_date_pst")
			private String originalPurchaseDatePst;
			@JsonProperty("is_trial_period")
			private String isTrialPeriod;
			@JsonProperty("in_app_ownership_type")
			private String inAppOwnershipType;
		}
	}
}
