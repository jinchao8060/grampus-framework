package com.oceancloud.grampus.framework.rabbitmq.loadbalancer;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import com.oceancloud.grampus.framework.core.utils.CollectionUtil;
import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.gray.constant.GrayLoadBalancerConstant;
import com.oceancloud.grampus.framework.rabbitmq.context.RabbitContextHolder;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.alibaba.nacos.client.utils.LogUtils.NAMING_LOGGER;

/**
 * GrayRabbitServiceInstanceLoadBalancer
 *
 * @author Beck
 * @since 2021-07-27
 */
@UtilityClass
public class GrayRabbitServiceInstanceLoadBalancer {

	public Instance getInstanceByVersion(String version) throws NacosException {
		List<Instance> instances = selectAllHealthyCurrentService();
		return getInstanceByVersion(version, instances);
	}

	public Instance getInstanceByVersionRandomWeight(String version) throws NacosException {
		List<Instance> instances = selectAllHealthyCurrentService();
		return getInstanceByVersionRandomWeight(version, instances);
	}

	private List<Instance> selectAllHealthyCurrentService() throws NacosException {
		String curServiceName = RabbitContextHolder.getCurServiceName();
		String curGroup = RabbitContextHolder.getCurGroup();
		return RabbitContextHolder.getNamingService().selectInstances(curServiceName, curGroup,true);
	}

	private Instance getInstanceByVersion(String version, List<Instance> instances) {
		if (CollectionUtil.isEmpty(instances)) {
			return null;
		}

		for (Instance instance : instances) {
			if (!instance.isHealthy()) {
				continue;
			}
			Map<String, String> metadata = instance.getMetadata();
			if (StringUtil.isNotBlank(version)
				&& version.equals(metadata.get(GrayLoadBalancerConstant.METADATA_GRAY_VERSION))) {
				return instance;
			}
		}

		return null;
	}

	/**
	 * Returns an instance with random-weight from the list of version-matched instances.
	 * if the list of version-matched instances is empty, then random-weight is used directly.
	 * Origin: com.alibaba.nacos.client.naming.core.Balancer
	 *
	 * @param version Request version.
	 * @param instances The list of the instance.
	 * @return The random-weight result of the instance
	 */
	private Instance getInstanceByVersionRandomWeight(String version, List<Instance> instances) {
		NAMING_LOGGER.debug("entry randomWithWeight");

		if (CollectionUtil.isEmpty(instances)) {
			NAMING_LOGGER.debug("hosts == null || hosts.size() == 0");
			return null;
		}
		List<Pair<Instance>> allHosts = new ArrayList<>();
		List<Pair<Instance>> matchVerHosts = new ArrayList<>();
		for (Instance instance : instances) {
			if (!instance.isHealthy()) {
				continue;
			}
			Map<String, String> metadata = instance.getMetadata();
			Pair<Instance> pair = new Pair<>(instance, instance.getWeight());
			allHosts.add(pair);
			if (StringUtil.isNotBlank(version)
				&& version.equals(metadata.get(GrayLoadBalancerConstant.METADATA_GRAY_VERSION))) {
				matchVerHosts.add(pair);
			}
		}

		List<Pair<Instance>> hostsWithWeight = CollectionUtil.isNotEmpty(
			matchVerHosts) ? matchVerHosts : allHosts;
		NAMING_LOGGER.debug("for (Host host : hosts)");

		Chooser<String, Instance> vipChooser = new Chooser<>("grampus-common-rabbit-lb");
		vipChooser.refresh(hostsWithWeight);
		NAMING_LOGGER.debug("vipChooser.refresh");

		return vipChooser.randomWithWeight();
	}
}
