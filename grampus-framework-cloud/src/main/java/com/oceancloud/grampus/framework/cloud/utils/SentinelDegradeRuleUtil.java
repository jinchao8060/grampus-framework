package com.oceancloud.grampus.framework.cloud.utils;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import lombok.experimental.UtilityClass;

/**
 * Sentinel降级规则构建工具
 *
 * 慢调用比例 (SLOW_REQUEST_RATIO)：选择以慢调用比例作为阈值，需要设置允许的慢调用 RT（即最大的响应时间），请求的响应时间大于该值则统计为慢调用。
 * 当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，并且慢调用的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。
 * 经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求响应时间小于设置的慢调用 RT 则结束熔断，若大于设置的慢调用 RT 则会再次被熔断。
 *
 * @author Beck
 * @since 2022-03-22
 */
@UtilityClass
public class SentinelDegradeRuleUtil {

	public DegradeRule buildResponseTimeDegradeRule(String resource) {
		DegradeRule degradeRule = new DegradeRule();
		degradeRule.setResource(resource);
		// 熔断策略：平均响应时间(DEGRADE_GRADE_RT):当平均RT超过阈值("DegradeRule"中的"count"，以毫秒为单位)时，资源将进入准降级状态。默认慢调用比例策略
		degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
		// 最大RT：最大响应时间阈值(毫秒)，超过该值资源将进入准降级状态。
		degradeRule.setCount(2000);
		// 熔断时长：断路器断开时的恢复超时时间(秒)。 超时后，断路器会转换为半开状态，尝试几个请求。
		degradeRule.setTimeWindow(5);
		// 最小请求数：触发断路的最小请求数(在一个活动统计时间范围内)。默认5次
		degradeRule.setMinRequestAmount(RuleConstant.DEGRADE_DEFAULT_MIN_REQUEST_AMOUNT);
		// 比例阈值：RT模式慢请求率阈值。
		degradeRule.setSlowRatioThreshold(0.8d);
		// 统计时长：统计间隔时长(毫秒)。默认1000ms
		degradeRule.setStatIntervalMs(1000);
		return degradeRule;
	}
}
