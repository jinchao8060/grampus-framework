package com.oceancloud.grampus.framework.gray.loadbalancer;

import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import com.oceancloud.grampus.framework.core.utils.CollectionUtil;
import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.gray.constant.GrayLoadBalancerConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alibaba.nacos.client.utils.LogUtils.NAMING_LOGGER;

/**
 * GrayWeightLoadBalancer
 *
 * @author Beck
 * @since 2021-06-28
 */
@Slf4j
public class GrayServiceInstanceLoadBalancer implements ReactorServiceInstanceLoadBalancer {

	final AtomicInteger position;

	final String serviceId;

	ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

	/**
	 * @param serviceInstanceListSupplierProvider a provider of
	 * {@link ServiceInstanceListSupplier} that will be used to get available instances
	 * @param serviceId id of the service for which to choose an instance
	 */
	public GrayServiceInstanceLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
										   String serviceId) {
		this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
	}

	/**
	 * @param serviceInstanceListSupplierProvider a provider of
	 * {@link ServiceInstanceListSupplier} that will be used to get available instances
	 * @param serviceId id of the service for which to choose an instance
	 * @param seedPosition Round Robin element position marker
	 */
	public GrayServiceInstanceLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
										   String serviceId, int seedPosition) {
		this.serviceId = serviceId;
		this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
		this.position = new AtomicInteger(seedPosition);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
				.getIfAvailable(NoopServiceInstanceListSupplier::new);
		return supplier.get(request).next()
				.map(serviceInstances -> processInstanceResponse(request, supplier, serviceInstances));
	}

	private Response<ServiceInstance> processInstanceResponse(Request request,
															  ServiceInstanceListSupplier supplier,
															  List<ServiceInstance> serviceInstances) {
		RequestDataContext requestContext = (RequestDataContext) request.getContext();
		HttpHeaders requestHeaders = requestContext.getClientRequest().getHeaders();
		Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(requestHeaders, serviceInstances);
		if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
			((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
		}
		return serviceInstanceResponse;
	}

	private Response<ServiceInstance> getInstanceResponse(HttpHeaders requestHeaders, List<ServiceInstance> instances) {
		String version = requestHeaders.getFirst(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION);
		ServiceInstance instance = getInstanceByVersionRandomWeight(version, instances);
		// NacosServiceDiscovery.hostToServiceInstance(instance, serviceId)
		return new DefaultResponse(instance);
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
	private ServiceInstance getInstanceByVersionRandomWeight(String version, List<ServiceInstance> instances) {
		NAMING_LOGGER.debug("entry randomWithWeight");

		if (CollectionUtil.isEmpty(instances)) {
			NAMING_LOGGER.debug("hosts == null || hosts.size() == 0");
			return null;
		}

		List<Pair<ServiceInstance>> allHosts = new ArrayList<>();
		List<Pair<ServiceInstance>> matchVerHosts = new ArrayList<>();
		for (ServiceInstance instance : instances) {
			Map<String, String> metadata = instance.getMetadata();
			String weight = metadata.get(GrayLoadBalancerConstant.METADATA_NACOS_WEIGHT);
			Pair<ServiceInstance> pair = new Pair<>(instance,
					StringUtil.isBlank(weight) ? 1 : Double.parseDouble(weight));
			allHosts.add(pair);
			if (StringUtil.isNotBlank(version)
					&& version.equals(metadata.get(GrayLoadBalancerConstant.METADATA_GRAY_VERSION))) {
				matchVerHosts.add(pair);
			}
		}

		List<Pair<ServiceInstance>> hostsWithWeight = CollectionUtil.isNotEmpty(
				matchVerHosts) ? matchVerHosts : allHosts;
		NAMING_LOGGER.debug("for (Host host : hosts)");

		Chooser<String, ServiceInstance> vipChooser = new Chooser<>("grampus-framework-gray");
		vipChooser.refresh(hostsWithWeight);
		NAMING_LOGGER.debug("vipChooser.refresh");

		return vipChooser.randomWithWeight();
	}
}
