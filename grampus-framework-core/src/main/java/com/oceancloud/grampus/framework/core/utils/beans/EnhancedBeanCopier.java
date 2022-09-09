package com.oceancloud.grampus.framework.core.utils.beans;

import com.oceancloud.grampus.framework.core.utils.ReflectUtil;
import com.oceancloud.grampus.framework.core.utils.StringUtil;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Label;
import org.springframework.asm.Opcodes;
import org.springframework.asm.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.cglib.core.ClassEmitter;
import org.springframework.cglib.core.CodeEmitter;
import org.springframework.cglib.core.Constants;
import org.springframework.cglib.core.Converter;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.cglib.core.Local;
import org.springframework.cglib.core.MethodInfo;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.core.TypeUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * EnhancedBeanCopier (support list,map)
 *
 * @author L.cm
 * @since 2021-01-25
 */
public abstract class EnhancedBeanCopier {
	private static final Type CONVERTER = TypeUtils.parseType(Converter.class.getName());
	private static final Type BEAN_COPIER = TypeUtils.parseType(EnhancedBeanCopier.class.getName());
	private static final Type BEAN_MAP = TypeUtils.parseType(Map.class.getName());
	private static final Signature COPY = new Signature("copy", Type.VOID_TYPE, new Type[]{Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER});
	private static final Signature CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object)");
	private static final Signature BEAN_MAP_GET = TypeUtils.parseSignature("Object get(Object)");
	private static final Type CLASS_UTILS = TypeUtils.parseType(ClassUtils.class.getName());
	private static final Signature IS_ASSIGNABLE_VALUE = TypeUtils.parseSignature("boolean isAssignableValue(Class, Object)");
	/**
	 * The map to store {@link EnhancedBeanCopier} of source type and class type for copy.
	 */
	private static final ConcurrentMap<EnhancedBeanCopierKey, EnhancedBeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

	public static EnhancedBeanCopier create(Class source, Class target, boolean useConverter) {
		return EnhancedBeanCopier.create(source, target, useConverter, false);
	}

	public static EnhancedBeanCopier create(Class source, Class target, boolean useConverter, boolean nonNull) {
		EnhancedBeanCopierKey copierKey = new EnhancedBeanCopierKey(source, target, useConverter, nonNull);
		return BEAN_COPIER_MAP.computeIfAbsent(copierKey, key -> {
			Generator gen = new Generator();
			gen.setSource(key.getSource());
			gen.setTarget(key.getTarget());
			gen.setUseConverter(key.isUseConverter());
			gen.setNonNull(key.isNonNull());
			return gen.create(key);
		});
	}

	/**
	 * Bean copy
	 *
	 * @param from      from Bean
	 * @param to        to Bean
	 * @param converter Converter
	 */
	abstract public void copy(Object from, Object to, @Nullable Converter converter);

	public static class Generator extends AbstractClassGenerator {
		private static final Source SOURCE = new Source(EnhancedBeanCopier.class.getName());
		private Class source;
		private Class target;
		private boolean useConverter;
		private boolean nonNull;

		Generator() {
			super(SOURCE);
		}

		public void setSource(Class source) {
			if (!Modifier.isPublic(source.getModifiers())) {
				setNamePrefix(source.getName());
			}
			this.source = source;
		}

		public void setTarget(Class target) {
			if (!Modifier.isPublic(target.getModifiers())) {
				setNamePrefix(target.getName());
			}
			this.target = target;
		}

		public void setUseConverter(boolean useConverter) {
			this.useConverter = useConverter;
		}

		public void setNonNull(boolean nonNull) {
			this.nonNull = nonNull;
		}

		@Override
		protected ClassLoader getDefaultClassLoader() {
			return target.getClassLoader();
		}

		@Override
		protected ProtectionDomain getProtectionDomain() {
			return ReflectUtils.getProtectionDomain(source);
		}

		@Override
		public EnhancedBeanCopier create(Object key) {
			return (EnhancedBeanCopier) super.create(key);
		}

		@Override
		public void generateClass(ClassVisitor v) {
			Type sourceType = Type.getType(source);
			Type targetType = Type.getType(target);
			ClassEmitter ce = new ClassEmitter(v);
			ce.begin_class(Constants.V1_2,
					Constants.ACC_PUBLIC,
					getClassName(),
					BEAN_COPIER,
					null,
					Constants.SOURCE_FILE);

			EmitUtils.null_constructor(ce);
			CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, COPY, null);

			if (Map.class.isAssignableFrom(source)) {
				generateClassFormMap(ce, e, sourceType, targetType);
				return;
			}

			PropertyDescriptor[] getters = ReflectUtil.getBeanGetters(source);
			PropertyDescriptor[] setters = ReflectUtil.getBeanSetters(target);
			Map<String, PropertyDescriptor> names = new HashMap<>(16);
			for (PropertyDescriptor getter : getters) {
				names.put(getter.getName(), getter);
			}

			Local targetLocal = e.make_local();
			Local sourceLocal = e.make_local();
			e.load_arg(1);
			e.checkcast(targetType);
			e.store_local(targetLocal);
			e.load_arg(0);
			e.checkcast(sourceType);
			e.store_local(sourceLocal);

			for (PropertyDescriptor setter : setters) {
				String propName = setter.getName();

				CopyProperty targetIgnoreCopy = ReflectUtil.getAnnotation(target, propName, CopyProperty.class);
				if (targetIgnoreCopy != null) {
					if (targetIgnoreCopy.ignore()) {
						continue;
					}
					String aliasTargetPropName = targetIgnoreCopy.value();
					if (StringUtil.isNotBlank(aliasTargetPropName)) {
						propName = aliasTargetPropName;
					}
				}
				PropertyDescriptor getter = names.get(propName);
				if (getter == null) {
					continue;
				}

				MethodInfo read = ReflectUtils.getMethodInfo(getter.getReadMethod());
				Method writeMethod = setter.getWriteMethod();
				MethodInfo write = ReflectUtils.getMethodInfo(writeMethod);
				Type returnType = read.getSignature().getReturnType();
				Type setterType = write.getSignature().getArgumentTypes()[0];
				Class<?> getterPropertyType = getter.getPropertyType();
				Class<?> setterPropertyType = setter.getPropertyType();

				// nonNull Label
				Label l0 = e.make_label();
				if (ClassUtils.isAssignable(setterPropertyType, getterPropertyType)) {
					e.load_local(targetLocal);
					e.load_local(sourceLocal);
					e.invoke(read);
					boolean getterIsPrimitive = getterPropertyType.isPrimitive();
					boolean setterIsPrimitive = setterPropertyType.isPrimitive();

					if (nonNull) {
						e.box(returnType);
						Local var = e.make_local();
						e.store_local(var);
						e.load_local(var);
						// nonNull Label
						e.ifnull(l0);
						e.load_local(targetLocal);
						e.load_local(var);
						e.unbox_or_zero(setterType);
					} else {
						if (getterIsPrimitive && !setterIsPrimitive) {
							e.box(returnType);
						}
						if (!getterIsPrimitive && setterIsPrimitive) {
							e.unbox_or_zero(setterType);
						}
					}
					invokeWrite(e, write, writeMethod, nonNull, l0);
				} else if (useConverter) {
					e.load_local(targetLocal);
					e.load_arg(2);
					e.load_local(sourceLocal);
					e.invoke(read);
					e.box(returnType);

					if (nonNull) {
						Local var = e.make_local();
						e.store_local(var);
						e.load_local(var);
						e.ifnull(l0);
						e.load_local(targetLocal);
						e.load_arg(2);
						e.load_local(var);
					}

					EmitUtils.load_class(e, setterType);
					e.push(propName);
					e.invoke_interface(CONVERTER, CONVERT);
					e.unbox_or_zero(setterType);

					invokeWrite(e, write, writeMethod, nonNull, l0);
				}
			}
			e.return_value();
			e.end_method();
			ce.end_class();
		}

		private static void invokeWrite(CodeEmitter e, MethodInfo write, Method writeMethod, boolean nonNull, Label l0) {
			Class<?> returnType = writeMethod.getReturnType();
			e.invoke(write);
			if (!returnType.equals(Void.TYPE)) {
				e.pop();
			}
			if (nonNull) {
				e.visitLabel(l0);
			}
		}

		@Override
		protected Object firstInstance(Class type) {
			return BeanUtils.instantiateClass(type);
		}

		@Override
		protected Object nextInstance(Object instance) {
			return instance;
		}

		/**
		 * 处理 map 的 copy
		 *
		 * @param ce         ClassEmitter
		 * @param e          CodeEmitter
		 * @param sourceType sourceType
		 * @param targetType targetType
		 */
		public void generateClassFormMap(ClassEmitter ce, CodeEmitter e, Type sourceType, Type targetType) {
			PropertyDescriptor[] setters = ReflectUtil.getBeanSetters(target);
			Local targetLocal = e.make_local();
			Local sourceLocal = e.make_local();
			e.load_arg(1);
			e.checkcast(targetType);
			e.store_local(targetLocal);
			e.load_arg(0);
			e.checkcast(sourceType);
			e.store_local(sourceLocal);
			Type mapBox = Type.getType(Object.class);

			for (PropertyDescriptor setter : setters) {
				String propName = setter.getName();
				CopyProperty targetIgnoreCopy = ReflectUtil.getAnnotation(target, propName, CopyProperty.class);
				if (targetIgnoreCopy != null) {
					if (targetIgnoreCopy.ignore()) {
						continue;
					}
					String aliasTargetPropName = targetIgnoreCopy.value();
					if (StringUtil.isNotBlank(aliasTargetPropName)) {
						propName = aliasTargetPropName;
					}
				}

				Method writeMethod = setter.getWriteMethod();
				MethodInfo write = ReflectUtils.getMethodInfo(writeMethod);
				Type setterType = write.getSignature().getArgumentTypes()[0];

				e.load_local(targetLocal);
				e.load_local(sourceLocal);

				e.push(propName);
				e.invoke_interface(BEAN_MAP, BEAN_MAP_GET);
				e.box(mapBox);

				Local var = e.make_local();
				e.store_local(var);
				e.load_local(var);

				Label l0 = e.make_label();
				e.ifnull(l0);
				EmitUtils.load_class(e, setterType);
				e.load_local(var);
				e.invoke_static(CLASS_UTILS, IS_ASSIGNABLE_VALUE, false);
				Label l1 = new Label();
				Class<?> returnType = writeMethod.getReturnType();
				if (useConverter) {
					e.if_jump(Opcodes.IFEQ, l1);
					e.load_local(targetLocal);
					e.load_local(var);
					e.unbox_or_zero(setterType);
					e.invoke(write);
					if (!returnType.equals(Void.TYPE)) {
						e.pop();
					}
					e.goTo(l0);
					e.visitLabel(l1);
					e.load_local(targetLocal);
					e.load_arg(2);
					e.load_local(var);
					EmitUtils.load_class(e, setterType);
					e.push(propName);
					e.invoke_interface(CONVERTER, CONVERT);
				} else {
					e.if_jump(Opcodes.IFEQ, l0);
					e.load_local(targetLocal);
					e.load_local(var);
				}
				e.unbox_or_zero(setterType);
				e.invoke(write);
				if (!returnType.equals(Void.TYPE)) {
					e.pop();
				}
				e.visitLabel(l0);
			}
			e.return_value();
			e.end_method();
			ce.end_class();
		}
	}
}
