package com.oceancloud.grampus.framework.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author L.cm
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class CollectionUtil extends org.springframework.util.CollectionUtils {

	private static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

	/**
	 * Return {@code true} if the supplied Collection is not {@code null} or empty.
	 * Otherwise, return {@code false}.
	 *
	 * @param collection the Collection to check
	 * @return whether the given Collection is not empty
	 */
	public static boolean isNotEmpty(@Nullable Collection<?> collection) {
		return !CollectionUtil.isEmpty(collection);
	}

	/**
	 * Return {@code true} if the supplied Map is not {@code null} or empty.
	 * Otherwise, return {@code false}.
	 *
	 * @param map the Map to check
	 * @return whether the given Map is not empty
	 */
	public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
		return !CollectionUtil.isEmpty(map);
	}

	/**
	 * Check whether the given Array contains the given element.
	 *
	 * @param array   the Array to check
	 * @param element the element to look for
	 * @param <T>     The generic tag
	 * @return {@code true} if found, {@code false} else
	 */
	public static <T> boolean contains(@Nullable T[] array, final T element) {
		if (array == null) {
			return false;
		}
		return Arrays.stream(array).anyMatch(x -> ObjectUtil.nullSafeEquals(x, element));
	}

	/**
	 * Concatenates 2 arrays
	 *
	 * @param one   数组1
	 * @param other 数组2
	 * @return 新数组
	 */
	public static String[] concat(String[] one, String[] other) {
		return concat(one, other, String.class);
	}

	/**
	 * Concatenates 2 arrays
	 *
	 * @param one   数组1
	 * @param other 数组2
	 * @param clazz 数组类
	 * @return 新数组
	 */
	public static <T> T[] concat(T[] one, T[] other, Class<T> clazz) {
		T[] target = (T[]) Array.newInstance(clazz, one.length + other.length);
		System.arraycopy(one, 0, target, 0, one.length);
		System.arraycopy(other, 0, target, one.length, other.length);
		return target;
	}

	/**
	 * Concatenates 2 arrays
	 *
	 * @param one   数组1
	 * @param other 数组2
	 * @return 新数组
	 */
	public static <T> T[] concat(T[] one, T[]... other) {
		int totalLength = one.length;
		for (T[] array : other) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(one, totalLength);
		int offset = one.length;
		for (T[] array : other) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * 不可变 Set
	 *
	 * @param es  对象
	 * @param <E> 泛型
	 * @return 集合
	 */
	@SafeVarargs
	public static <E> Set<E> ofImmutableSet(E... es) {
		return Arrays.stream(Objects.requireNonNull(es, "args es is null.")).collect(Collectors.toSet());
	}

	/**
	 * 不可变 List
	 *
	 * @param es  对象
	 * @param <E> 泛型
	 * @return 集合
	 */
	@SafeVarargs
	public static <E> List<E> ofImmutableList(E... es) {
		return Arrays.stream(Objects.requireNonNull(es, "args es is null.")).collect(Collectors.toList());
	}

	/**
	 * Iterable 转换为List集合
	 *
	 * @param elements Iterable
	 * @param <E>      泛型
	 * @return 集合
	 */
	public static <E> List<E> toList(Iterable<E> elements) {
		Objects.requireNonNull(elements, "elements es is null.");
		if (elements instanceof Collection) {
			return new ArrayList<>((Collection<E>) elements);
		}
		Iterator<E> iterator = elements.iterator();
		List<E> list = new ArrayList<>();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}

	/**
	 * 将key value 数组转为 map
	 *
	 * @param keysValues key value 数组
	 * @param <K>        key
	 * @param <V>        value
	 * @return map 集合
	 */
	public static <K, V> Map<K, V> toMap(Object... keysValues) {
		int kvLength = keysValues.length;
		if (kvLength % 2 != 0) {
			throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
		}
		Map<K, V> keyValueMap = new HashMap<>(kvLength);
		for (int i = kvLength - 2; i >= 0; i -= 2) {
			Object key = keysValues[i];
			Object value = keysValues[i + 1];
			keyValueMap.put((K) key, (V) value);
		}
		return keyValueMap;
	}

	/**
	 * 创建默认HashMap
	 *
	 * @param <K> K
	 * @param <V> V
	 * @return HashMap
	 * @see com.google.common.collect.Maps#newHashMap()
	 * @since 3.4.0
	 */
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<>();
	}

	/**
	 * 根据预期大小创建HashMap.
	 *
	 * @param expectedSize 预期大小
	 * @param <K>          K
	 * @param <V>          V
	 * @return HashMap
	 * @see com.google.common.collect.Maps#newHashMapWithExpectedSize
	 * @since 3.4.0
	 */
	public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
		return new HashMap<>(capacity(expectedSize));
	}

	/**
	 * 用来过渡下Jdk1.8下ConcurrentHashMap的性能bug
	 * https://bugs.openjdk.java.net/browse/JDK-8161372
	 *
	 * @param concurrentHashMap ConcurrentHashMap 没限制类型了，非ConcurrentHashMap就别调用这方法了
	 * @param key               key
	 * @param mappingFunction   function
	 * @param <K>               k
	 * @param <V>               v
	 * @return V
	 * @since 3.4.0
	 */
	public static <K, V> V computeIfAbsent(Map<K, V> concurrentHashMap, K key, Function<? super K, ? extends V> mappingFunction) {
		V v = concurrentHashMap.get(key);
		if (v != null) {
			return v;
		}
		return concurrentHashMap.computeIfAbsent(key, mappingFunction);
	}

	/**
	 * Returns a capacity that is sufficient to keep the map from being resized as
	 * long as it grows no larger than expectedSize and the load factor is >= its
	 * default (0.75).
	 *
	 * @see com.google.common.collect.Maps#capacity(int)
	 * @since 3.4.0
	 */
	private static int capacity(int expectedSize) {
		if (expectedSize < 3) {
			if (expectedSize < 0) {
				throw new IllegalArgumentException("expectedSize cannot be negative but was: " + expectedSize);
			}
			return expectedSize + 1;
		}
		if (expectedSize < MAX_POWER_OF_TWO) {
			// This is the calculation used in JDK8 to resize when a putAll
			// happens; it seems to be the most conservative calculation we
			// can make.  0.75 is the default load factor.
			return (int) ((float) expectedSize / 0.75F + 1.0F);
		}
		return Integer.MAX_VALUE; // any large value
	}

	/**
	 * list 分片
	 *
	 * @param list List
	 * @param size 分片大小
	 * @param <T>  泛型
	 * @return List 分片
	 */
	public static <T> List<List<T>> partition(List<T> list, int size) {
		Objects.requireNonNull(list, "List to partition must not null.");
		Assert.isTrue(size > 0, "List to partition size must more then zero.");
		return (list instanceof RandomAccess)
				? new RandomAccessPartition<>(list, size)
				: new Partition<>(list, size);
	}

	private static class RandomAccessPartition<T> extends Partition<T> implements RandomAccess {
		RandomAccessPartition(List<T> list, int size) {
			super(list, size);
		}
	}

	private static class Partition<T> extends AbstractList<List<T>> {
		final List<T> list;
		final int size;

		Partition(List<T> list, int size) {
			this.list = list;
			this.size = size;
		}

		@Override
		public List<T> get(int index) {
			if (index >= 0 && index < this.size()) {
				int start = index * this.size;
				int end = Math.min(start + this.size, this.list.size());
				return this.list.subList(start, end);
			}
			throw new IndexOutOfBoundsException(String.format("index (%s) must be less than size (%s)", index, this.size()));
		}

		@Override
		public int size() {
			return ceilDiv(this.list.size(), this.size);
		}

		@Override
		public boolean isEmpty() {
			return this.list.isEmpty();
		}

		private static int ceilDiv(int x, int y) {
			int r = x / y;
			if (r * y < x) {
				r++;
			}
			return r;
		}
	}

}
