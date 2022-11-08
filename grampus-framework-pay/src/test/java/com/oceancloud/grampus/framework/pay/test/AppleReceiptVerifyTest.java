package com.oceancloud.grampus.framework.pay.test;

import com.oceancloud.grampus.framework.pay.apple.bean.AppleReceiptData;
import com.oceancloud.grampus.framework.pay.apple.utils.AppleReceiptVerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * AppleReceiptVerifyTest
 *
 * @author Beck
 * @since 2022-10-20
 */
@Slf4j
public class AppleReceiptVerifyTest {

	private static final String receipt = "";

	@Test
	public void test() {
		AppleReceiptData appleReceiptData = AppleReceiptVerifyUtil.verifyReceipt(receipt);
		log.info("{}", appleReceiptData);
	}
}
