package com.oceancloud.grampus.framework.cloud.client;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * NacosServiceInstanceRetrofitClient
 *
 * @author Beck
 * @since 2022-01-16
 */
public interface NacosServiceInstanceRetrofitClient {

	/**
	 * 更新实例信息
	 * curl -X PUT 'http://localhost:8848/nacos/v1/ns/instance?serviceName=grampus-api&clusterName=DEFAULT&groupName=GRAMPUS_CLOUD_GROUP&ip=192.168.8.245&port=7290&ephemeral=true&weight=1&enabled=true&metadata=%7B%22preserved.register.source%22%3A%22SPRING_CLOUD%22%2C%22management.context-path%22%3A%22%2Factuator%22%2C%22gray.version%22%3A%221.0.0%22%7D&namespaceId=grampus-cloud'
	 *
	 * @param serviceName 服务名
	 * @param groupName   分组名
	 * @param ip          IP地址
	 * @param port        端口号
	 * @param clusterName 集群名称
	 * @param namespaceId 命名空间ID
	 * @param weight      权重
	 * @param metadata    扩展信息
	 * @param enabled     是否打开流量
	 * @param ephemeral   是否临时实例
	 * @return 响应数据
	 */
	@PUT("/nacos/v1/ns/instance")
	Call<String> updateInstance(@Query("serviceName") String serviceName,
								@Query("groupName") String groupName,
								@Query("ip") String ip,
								@Query("port") Integer port,
								@Query("clusterName") String clusterName,
								@Query("namespaceId") String namespaceId,
								@Query("weight") Float weight,
								@Query("metadata") String metadata,
								@Query("enabled") Boolean enabled,
								@Query("ephemeral") Boolean ephemeral);
}
