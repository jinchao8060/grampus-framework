package com.oceancloud.grampus.framework.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oceancloud.grampus.framework.core.utils.BeanUtil;
import com.oceancloud.grampus.framework.core.utils.ReflectUtil;
import com.oceancloud.grampus.framework.mybatis.mapper.BaseMapper;
import com.oceancloud.grampus.framework.mybatis.page.PageData;
import com.oceancloud.grampus.framework.mybatis.page.PageQuery;
import com.oceancloud.grampus.framework.mybatis.service.EnhancedBaseService;

import java.io.Serializable;
import java.util.List;

/**
 * 增强的基础服务类，简化Entity与DTO的转换
 *
 * @author Beck
 * @since 2021-04-12
 */
public class EnhancedBaseServiceImpl<M extends BaseMapper<T>, T, D> extends BaseServiceImpl<M, T> implements EnhancedBaseService<T, D> {

	protected Class<D> getCurrentDtoClass() {
		return (Class<D>) ReflectUtil.getSuperClassGenericType(getClass(), 2);
	}

	@Override
	public void saveOne(D dto) {
		T entity = BeanUtil.copy(dto, getCurrentEntityClass());
		save(entity);
		BeanUtil.copy(entity, dto);
	}

	@Override
	public void saveBatch(List<D> dtoList) {
		saveBatch(dtoList, 100);
	}

	@Override
	public void saveBatch(List<D> dtoList, int batchSize) {
		List<T> entityList = BeanUtil.copyList(dtoList, getCurrentEntityClass());
		saveBatch(entityList, batchSize);
		BeanUtil.copyList(entityList, dtoList, getCurrentDtoClass());
	}

	@Override
	public void modifyById(D dto) {
		T entity = BeanUtil.copy(dto, getCurrentEntityClass());
		updateById(entity);
	}

	@Override
	public D queryById(Serializable id) {
		T result = getById(id);
		return BeanUtil.copy(result, getCurrentDtoClass());
	}

	@Override
	public D queryOne(D dto) {
		T params = BeanUtil.copy(dto, getCurrentEntityClass());
		T result = getOne(new QueryWrapper<>(params));
		return BeanUtil.copy(result, getCurrentDtoClass());
	}

	@Override
	public List<D> queryList(D dto) {
		T params = BeanUtil.copy(dto, getCurrentEntityClass());
		List<T> result = list(new QueryWrapper<>(params));
		return BeanUtil.copyList(result, getCurrentDtoClass());
	}

//	@Override
//	public List<D> queryList(Map<String, Object> params) {
//		List<T> result = selectList(params, getCurrentEntityClass());
//		return BeanUtil.copyList(result, getCurrentDtoClass());
//	}

	@Override
	public List<D> queryAll() {
		List<T> result = list();
		return BeanUtil.copyList(result, getCurrentDtoClass());
	}

	@Override
	public long queryCount(D dto) {
		T params = BeanUtil.copy(dto, getCurrentEntityClass());
		return count(new QueryWrapper<>(params));
	}
//
//	@Override
//	public PageData<D> queryPage(PageQuery pageQuery, Object entityQuery) {
//		PageData<T> result = selectPage(pageQuery, entityQuery, getCurrentEntityClass());
//		List<D> dList = BeanUtil.copyList(result.getList(), getCurrentDtoClass());
//		return new PageData<>(result, dList);
//	}
//
//	@Override
//	public PageData<D> queryPage(Map<String, Object> params) {
//		PageData<T> result = selectPage(params, getCurrentEntityClass());
//		List<D> dList = BeanUtil.copyList(result.getList(), getCurrentDtoClass());
//		return new PageData<>(result, dList);
//	}
//
//	@Override
//	public PageData<D> queryPage(D dto, int pageNum, int pageSize) {
//		return this.queryPage(dto, pageNum, pageSize, true);
//	}
//
//	@Override
//	public PageData<D> queryPage(D dto, int pageNum, int pageSize, boolean withCount) {
//		return this.queryPage(dto, pageNum, pageSize, withCount, null);
//	}
//
//	@Override
//	public PageData<D> queryPage(D dto, int pageNum, int pageSize, boolean withCount, String order) {
//		T entity = BeanUtil.copy(dto, getCurrentEntityClass());
//		PageData<T> result = selectPage(entity, pageNum, pageSize, withCount, order);
//		List<D> dList = BeanUtil.copyList(result.getList(), getCurrentDtoClass());
//		return new PageData<>(result, dList);
//	}

	@Override
	public PageData<D> queryPage(PageQuery pageQuery, Object entityQuery) {
		T entity = BeanUtil.copy(entityQuery, getCurrentEntityClass());
		PageData<T> result = page(pageQuery, new QueryWrapper<>(entity));
		List<D> dList = BeanUtil.copyList(result.getList(), getCurrentDtoClass());
		return new PageData<>(result, dList);
	}

	@Override
	public PageData<D> queryPage(D dto, int pageNum, int pageSize) {
		return this.queryPage(dto, pageNum, pageSize, true);
	}

	@Override
	public PageData<D> queryPage(D dto, int pageNum, int pageSize, boolean withCount) {
		return this.queryPage(dto, pageNum, pageSize, withCount, null);
	}

	@Override
	public PageData<D> queryPage(D dto, int pageNum, int pageSize, boolean withCount, String order) {
		T entity = BeanUtil.copy(dto, getCurrentEntityClass());
		PageData<T> result = page(new QueryWrapper<>(entity), pageNum, pageSize, withCount, order);
		List<D> dList = BeanUtil.copyList(result.getList(), getCurrentDtoClass());
		return new PageData<>(result, dList);
	}
}
