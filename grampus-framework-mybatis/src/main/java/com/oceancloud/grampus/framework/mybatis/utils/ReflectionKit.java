package com.oceancloud.grampus.framework.mybatis.utils;

import com.oceancloud.grampus.framework.core.utils.CollectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * 反射工具类，提供反射相关的快捷操作
 *
 * @author Caratacus
 * @author hcl
 * @since 2016-09-22
 */
public final class ReflectionKit {
	/**
	 * class field cache
	 */
	private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();

	/**
	 * <p>
	 * 获取该类的所有属性列表
	 * </p>
	 *
	 * @param clazz 反射类
	 */
	public static List<Field> getFieldList(Class<?> clazz) {
		if (Objects.isNull(clazz)) {
			return Collections.emptyList();
		}
		return CollectionUtil.computeIfAbsent(CLASS_FIELD_CACHE, clazz, k -> {
			Field[] fields = k.getDeclaredFields();
			List<Field> superFields = new ArrayList<>();
			Class<?> currentClass = k.getSuperclass();
			while (currentClass != null) {
				Field[] declaredFields = currentClass.getDeclaredFields();
				Collections.addAll(superFields, declaredFields);
				currentClass = currentClass.getSuperclass();
			}
			/* 排除重载属性 */
			Map<String, Field> fieldMap = excludeOverrideSuperField(fields, superFields);
			/*
			 * 重写父类属性过滤后处理忽略部分，支持过滤父类属性功能
			 * 场景：中间表不需要记录创建时间，忽略父类 createTime 公共属性
			 * 中间表实体重写父类属性 ` private transient Date createTime; `
			 */
			return fieldMap.values().stream()
					/* 过滤静态属性 */
					.filter(f -> !Modifier.isStatic(f.getModifiers()))
					/* 过滤 transient关键字修饰的属性 */
					.filter(f -> !Modifier.isTransient(f.getModifiers()))
					.collect(Collectors.toList());
		});
	}

	/**
	 * <p>
	 * 排序重置父类属性
	 * </p>
	 *
	 * @param fields         子类属性
	 * @param superFieldList 父类属性
	 */
	public static Map<String, Field> excludeOverrideSuperField(Field[] fields, List<Field> superFieldList) {
		// 子类属性
		Map<String, Field> fieldMap = Stream.of(fields).collect(toMap(Field::getName, identity(),
				(u, v) -> {
					throw new IllegalStateException(String.format("Duplicate key %s", u));
				},
				LinkedHashMap::new));
		superFieldList.stream().filter(field -> !fieldMap.containsKey(field.getName()))
				.forEach(f -> fieldMap.put(f.getName(), f));
		return fieldMap;
	}
}

