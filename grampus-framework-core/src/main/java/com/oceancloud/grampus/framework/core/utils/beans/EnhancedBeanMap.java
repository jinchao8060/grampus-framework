package com.oceancloud.grampus.framework.core.utils.beans;

import org.springframework.asm.ClassVisitor;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.cglib.core.ReflectUtils;

import java.security.ProtectionDomain;

/**
 * EnhancedBeanMap
 *
 * @author L.cm
 * @since 2021-01-25
 */
public abstract class EnhancedBeanMap extends BeanMap {
	protected EnhancedBeanMap() {
	}

	protected EnhancedBeanMap(Object bean) {
		super(bean);
	}

	public static EnhancedBeanMap create(Object bean) {
		EnhancedGenerator gen = new EnhancedGenerator();
		gen.setBean(bean);
		return gen.create();
	}

	/**
	 * newInstance
	 *
	 * @param o Object
	 * @return EnhancedBeanMap
	 */
	@Override
	public abstract EnhancedBeanMap newInstance(Object o);

	public static class EnhancedGenerator extends AbstractClassGenerator {
		private static final Source SOURCE = new Source(EnhancedBeanMap.class.getName());

		private Object bean;
		private Class beanClass;
		private int require;

		public EnhancedGenerator() {
			super(SOURCE);
		}

		/**
		 * Set the bean that the generated map should reflect. The bean may be swapped
		 * out for another bean of the same type using {@link #setBean}.
		 * Calling this method overrides any value previously set using {@link #setBeanClass}.
		 * You must call either this method or {@link #setBeanClass} before {@link #create}.
		 *
		 * @param bean the initial bean
		 */
		public void setBean(Object bean) {
			this.bean = bean;
			if (bean != null) {
				beanClass = bean.getClass();
			}
		}

		/**
		 * Set the class of the bean that the generated map should support.
		 * You must call either this method or {@link #setBeanClass} before {@link #create}.
		 *
		 * @param beanClass the class of the bean
		 */
		public void setBeanClass(Class beanClass) {
			this.beanClass = beanClass;
		}

		/**
		 * Limit the properties reflected by the generated map.
		 *
		 * @param require any combination of {@link #REQUIRE_GETTER} and
		 *                {@link #REQUIRE_SETTER}; default is zero (any property allowed)
		 */
		public void setRequire(int require) {
			this.require = require;
		}

		@Override
		protected ClassLoader getDefaultClassLoader() {
			return beanClass.getClassLoader();
		}

		@Override
		protected ProtectionDomain getProtectionDomain() {
			return ReflectUtils.getProtectionDomain(beanClass);
		}

		/**
		 * Create a new instance of the <code>BeanMap</code>. An existing
		 * generated class will be reused if possible.
		 *
		 * @return {EnhancedBeanMap}
		 */
		public EnhancedBeanMap create() {
			if (beanClass == null) {
				throw new IllegalArgumentException("Class of bean unknown");
			}
			setNamePrefix(beanClass.getName());
			EnhancedBeanMapKey key = new EnhancedBeanMapKey(beanClass, require);
			return (EnhancedBeanMap) super.create(key);
		}

		@Override
		public void generateClass(ClassVisitor v) throws Exception {
			new EnhancedBeanMapEmitter(v, getClassName(), beanClass, require);
		}

		@Override
		protected Object firstInstance(Class type) {
			return ((BeanMap) ReflectUtils.newInstance(type)).newInstance(bean);
		}

		@Override
		protected Object nextInstance(Object instance) {
			return ((BeanMap) instance).newInstance(bean);
		}
	}

}
