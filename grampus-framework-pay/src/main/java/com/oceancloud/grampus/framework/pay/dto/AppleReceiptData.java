package com.oceancloud.grampus.framework.pay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 苹果票据数据实体
 * <a href="https://developer.apple.com/documentation/appstorereceipts/responsebody">responsebody</a>
 *
 * @author Beck
 * @since 2022-10-20
 */
@NoArgsConstructor
@Data
public class AppleReceiptData {
	/**
	 * 您发送以供验证的收据的 JSON 表示形式。
	 */
	@JsonProperty("receipt")
	private ReceiptDTO receipt;
	/**
	 * 系统为其生成收据的环境。
	 * 可能的值：Sandbox, Production
	 */
	@JsonProperty("environment")
	private String environment;
	/**
	 * 如果收据有效，或者0如果有错误，则返回状态代码。状态码反映了整个应用收据的状态。<a href="https://developer.apple.com/documentation/appstorereceipts/status">有关status可能的状态代码和说明，请参阅。</a>
	 */
	@JsonProperty("status")
	private Integer status;

	@NoArgsConstructor
	@Data
	public static class ReceiptDTO {
		@JsonProperty("receipt_type")
		private String receiptType;
		/**
		 * app_item_id
		 */
		@JsonProperty("adam_id")
		private Integer adamId;
		/**
		 * 由 App Store Connect 生成并由 App Store 用于唯一标识所购买的应用程序。仅在生产中为应用分配此标识符。将此值视为 64 位长整数。
		 */
		@JsonProperty("app_item_id")
		private Integer appItemId;
		/**
		 * 收据所属应用的捆绑包标识符。您在 App Store Connect 上提供此字符串。这对应于应用程序文件中的值。CFBundleIdentifierInfo.plist
		 */
		@JsonProperty("bundle_id")
		private String bundleId;
		/**
		 * 应用程序的版本号。应用程序的版本号对应于. 在生产中，此值是设备上基于. 在沙盒中，该值始终为。CFBundleVersionCFBundleShortVersionStringInfo.plistreceipt_creation_date_ms"1.0"
		 */
		@JsonProperty("application_version")
		private String applicationVersion;
		/**
		 * 应用下载交易的唯一标识符。
		 */
		@JsonProperty("download_id")
		private Long downloadId;
		/**
		 * 一个任意数字，用于标识您的应用程序的修订版。在沙箱中，此键的值为“0”。
		 */
		@JsonProperty("version_external_identifier")
		private Integer versionExternalIdentifier;
		/**
		 * App Store 生成收据的时间，采用类似于 ISO 8601 的日期时间格式。
		 */
		@JsonProperty("receipt_creation_date")
		private String receiptCreationDate;
		/**
		 * App Store 生成收据的时间，采用 UNIX 纪元时间格式，以毫秒为单位。使用此时间格式处理日期。这个值不会改变。
		 */
		@JsonProperty("receipt_creation_date_ms")
		private String receiptCreationDateMs;
		/**
		 * App Store 生成收据的时间，在太平洋时区。
		 */
		@JsonProperty("receipt_creation_date_pst")
		private String receiptCreationDatePst;
		/**
		 * 处理对端点的请求并生成响应的时间，采用类似于 ISO 8601 的日期时间格式。verifyReceipt
		 */
		@JsonProperty("request_date")
		private String requestDate;
		/**
		 * 处理对端点的请求并生成响应的时间，采用 UNIX 纪元时间格式，以毫秒为单位。使用此时间格式处理日期。verifyReceipt
		 */
		@JsonProperty("request_date_ms")
		private String requestDateMs;
		/**
		 * 在太平洋时区处理对端点的请求并生成响应的时间。verifyReceipt
		 */
		@JsonProperty("request_date_pst")
		private String requestDatePst;
		/**
		 * 原始应用购买的时间，采用类似于 ISO 8601 的日期时间格式。
		 */
		@JsonProperty("original_purchase_date")
		private String originalPurchaseDate;
		/**
		 * 原始应用购买的时间，采用 UNIX 纪元时间格式，以毫秒为单位。使用此时间格式处理日期。
		 */
		@JsonProperty("original_purchase_date_ms")
		private String originalPurchaseDateMs;
		/**
		 * 原始应用购买时间，太平洋时区。
		 */
		@JsonProperty("original_purchase_date_pst")
		private String originalPurchaseDatePst;
		/**
		 * 用户最初购买的应用版本。该值不变，对应原始购买文件中（iOS 中）或String（macOS 中）的值。在沙盒环境中，该值始终为.CFBundleVersionCFBundleShortVersionInfo.plist"1.0"
		 */
		@JsonProperty("original_application_version")
		private String originalApplicationVersion;
		/**
		 * 包含所有应用内购买交易的应用内购买收据字段的数组。
		 */
		@JsonProperty("in_app")
		private List<InAppDTO> inApp;

		@NoArgsConstructor
		@Data
		public static class InAppDTO {
			/**
			 * 购买的消耗品数量。此值对应于SKPayment存储在交易的支付属性中的对象的数量属性。“1”除非使用可变付款进行修改，否则该值通常是不变的。最大值为 10。
			 */
			@JsonProperty("quantity")
			private String quantity;
			/**
			 * 购买的产品的唯一标识符。您在 App Store Connect 中创建产品时提供此值，它对应于存储在交易的支付属性中的对象的属性。productIdentifierSKPayment
			 */
			@JsonProperty("product_id")
			private String productId;
			/**
			 * 交易的唯一标识符，例如购买、恢复或续订。有关更多信息，请参阅。transaction_id
			 */
			@JsonProperty("transaction_id")
			private String transactionId;
			/**
			 * 原始购买的交易标识符。有关更多信息，请参阅。original_transaction_id
			 */
			@JsonProperty("original_transaction_id")
			private String originalTransactionId;
			/**
			 * App Store 向用户帐户收取购买或恢复产品费用的时间，或 App Store 向用户帐户收取订阅购买或续订费用的时间，采用类似于 ISO 8601 的日期时间格式。
			 */
			@JsonProperty("purchase_date")
			private String purchaseDate;
			/**
			 * 对于消耗性、非消耗性和非续订订阅产品，App Store 向用户帐户收取购买或恢复产品的时间，采用 UNIX 纪元时间格式，以毫秒为单位。对于自动续订订阅，App Store 向用户帐户收取订阅购买或过期后续订的时间，采用 UNIX 纪元时间格式，以毫秒为单位。使用此时间格式处理日期。
			 */
			@JsonProperty("purchase_date_ms")
			private String purchaseDateMs;
			/**
			 * 在太平洋时区，App Store 向用户帐户收取购买或恢复产品费用的时间，或 App Store 向用户帐户收取订阅购买或续订费用的时间。
			 */
			@JsonProperty("purchase_date_pst")
			private String purchaseDatePst;
			/**
			 * 原始应用内购买的时间，采用类似于 ISO 8601 的日期时间格式。
			 */
			@JsonProperty("original_purchase_date")
			private String originalPurchaseDate;
			/**
			 * 原始应用内购买的时间，采用 UNIX 纪元时间格式，以毫秒为单位。对于自动续订订阅，此值表示订阅的初始购买日期。原始购买日期适用于所有产品类型，并且在相同产品 ID 的所有交易中保持相同。此值对应于 StoreKit 中原始交易的属性。使用此时间格式处理日期。transactionDate
			 */
			@JsonProperty("original_purchase_date_ms")
			private String originalPurchaseDateMs;
			/**
			 * 原始应用内购买的时间，在太平洋时区。
			 */
			@JsonProperty("original_purchase_date_pst")
			private String originalPurchaseDatePst;
			/**
			 * 指示订阅是否处于免费试用期。有关更多信息，请参阅。is_trial_period
			 */
			@JsonProperty("is_trial_period")
			private String isTrialPeriod;
			@JsonProperty("in_app_ownership_type")
			private String inAppOwnershipType;
		}
	}
}
