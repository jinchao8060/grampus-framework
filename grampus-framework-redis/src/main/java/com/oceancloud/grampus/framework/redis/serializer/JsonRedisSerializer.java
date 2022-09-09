package com.oceancloud.grampus.framework.redis.serializer;

import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * Redis 自定义序列化
 *
 * @author Beck
 * @since 2021-04-02
 */
public class JsonRedisSerializer<T> implements RedisSerializer<T> {

	private Class<T> type;

	public JsonRedisSerializer(Class<T> type) {
		this.type = type;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return JSONUtil.writeValueAsString(t).getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		return JSONUtil.readValue(new String(bytes, StandardCharsets.UTF_8), type);
	}
}