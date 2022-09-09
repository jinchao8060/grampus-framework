package com.oceancloud.grampus.framework.gray.test;

import com.oceancloud.grampus.framework.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * GrayTest
 *
 * @author Beck
 * @since 2021-07-02
 */
@Slf4j
public class GrayTest {

	@Test
	public void graytest() {
		String ipStr;
		ipStr = "H001,H002";
		String[] ipArr = execute(ipStr);

		double test = Double.parseDouble("100D");
		log.debug("ipStr:{} data:{} test:{}", ipStr, ipArr[1], test);
	}

	private String[] execute(String ipStr) {
		if (StringUtil.isBlank(ipStr)) {
			return null;
		}
		String[] ipArr = ipStr.split(",");
		if (ipArr.length == 0) {
			return null;
		}
		return ipArr;
	}
}
