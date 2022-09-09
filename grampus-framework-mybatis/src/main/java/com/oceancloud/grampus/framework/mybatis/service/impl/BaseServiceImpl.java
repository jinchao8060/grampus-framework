package com.oceancloud.grampus.framework.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.oceancloud.grampus.framework.core.constant.Constant;
import com.oceancloud.grampus.framework.core.utils.ReflectUtil;
import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.mybatis.mapper.BaseMapper;
import com.oceancloud.grampus.framework.mybatis.page.PageData;
import com.oceancloud.grampus.framework.mybatis.page.PageQuery;
import com.oceancloud.grampus.framework.mybatis.service.BaseService;
import com.oceancloud.grampus.framework.mybatis.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BaseServiceImpl
 *
 * @author Beck
 * @since 2022-04-11
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

	protected Class<T> getCurrentEntityClass() {
		return (Class<T>) ReflectUtil.getSuperClassGenericType(getClass(), 1);
	}

	@Autowired
	protected M baseMapper;

//	@Override
//	public void insert(T entity) {
//		BeanUtil.setProperty(entity, "id", null);
//		baseMapper.insert(entity);
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void insertBatch(List<T> entityList) {
//		this.insertBatch(entityList, 100);
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void insertBatch(List<T> entityList, int batchSize) {
//		if (CollectionUtils.isEmpty(entityList)) {
//			throw new IllegalArgumentException("Error: entityList must not be empty");
//		}
//		if (entityList.size() <= batchSize) {
//			for (T entity : entityList) {
//				BeanUtil.setProperty(entity, "id", null);
//				baseMapper.insert(entity);
//			}
//		}
//		List<List<T>> partitionList = Lists.partition(entityList, batchSize);
//		partitionList.forEach(anEntityList -> {
//			for (T entity : entityList) {
//				BeanUtil.setProperty(entity, "id", null);
//				baseMapper.insert(entity);
//			}
//		});
//	}
//
//	@Override
//	public void updateById(T entity) {
//		baseMapper.updateById(entity);
//	}
//
//	@Override
//	public void update(T entity, Wrapper<T> wrapper) {
//		baseMapper.update(entity, wrapper);
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void updateBatchById(List<T> entityList) {
//		this.updateBatchById(entityList, 100);
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void updateBatchById(List<T> entityList, int batchSize) {
//		if (CollectionUtils.isEmpty(entityList)) {
//			throw new IllegalArgumentException("Error: entityList must not be empty");
//		}
//		if (entityList.size() <= batchSize) {
//			for (T entity : entityList) {
//				baseMapper.updateById(entity);
//			}
//		}
//		List<List<T>> partitionList = Lists.partition(entityList, batchSize);
//		partitionList.forEach(anEntityList -> {
//			for (T entity : entityList) {
//				baseMapper.updateById(entity);
//			}
//		});
//	}
//
//	@Override
//	public T selectById(Serializable id) {
//		return baseMapper.selectById(id);
//	}
//
//	@Override
//	public T selectOne(T entity) {
//		return baseMapper.selectOne(new QueryWrapper<>(entity));
//	}
//
//	@Override
//	public List<T> selectList(T entity) {
//		return baseMapper.selectList(new QueryWrapper<>(entity));
//	}
//
//	@Override
//	public List<T> selectList(Map<String, Object> params, Class<T> clazz) {
//		T entity = BeanUtil.toBean(params, clazz, true);
//		return this.selectList(entity);
//	}
//
//	@Override
//	public List<T> selectAll() {
//		return baseMapper.selectList(new QueryWrapper<>());
//	}
//
//	@Override
//	public long selectCount(T entity) {
//		return baseMapper.selectCount(new QueryWrapper<>(entity));
//	}
//
//	@Override
//	public PageData<T> selectPage(PageQuery pageQuery, Object entityQuery, Class<T> clazz) {
//		T entity = BeanUtil.copyWithConvert(entityQuery, clazz);
//		// 分页字段
//		int pageNum = pageQuery != null && pageQuery.getPageNum() != null ? pageQuery.getPageNum() : 1;
//		int pageSize = pageQuery != null && pageQuery.getPageSize() != null ? pageQuery.getPageSize() : 10;
//		// 排序字段
//		String order = pageQuery != null && StringUtil.isNotBlank(pageQuery.getOrder()) ? pageQuery.getOrder() : null;
//		// 是否查count
//		boolean withCount = pageQuery != null && pageQuery.getWithCount() != null ? pageQuery.getWithCount() : true;
//		return this.selectPage(entity, pageNum, pageSize, withCount, order);
//	}
//
//	@Override
//	public PageData<T> selectPage(Map<String, Object> params, Class<T> clazz) {
//		T entity = BeanUtil.toBean(params, clazz, true);
//		String pageNumStr = (String) params.get(Constant.PAGE_NUM);
//		String pageSizeStr = (String) params.get(Constant.PAGE_SIZE);
//		String withCountStr = (String) params.get(Constant.WITH_COUNT);
//		// 分页字段
//		int pageNum = params.containsKey(Constant.PAGE_NUM) ? Integer.parseInt(pageNumStr) : 1;
//		int pageSize = params.containsKey(Constant.PAGE_SIZE) ? Integer.parseInt(pageSizeStr) : 10;
//		// 排序字段
//		String order = (String) params.get(Constant.ORDER);
//		// 是否查count
//		boolean withCount = !params.containsKey(Constant.WITH_COUNT) || Boolean.parseBoolean(withCountStr);
//		return this.selectPage(entity, pageNum, pageSize, withCount, order);
//	}
//
//	@Override
//	public PageData<T> selectPage(T entity, int pageNum, int pageSize) {
//		return this.selectPage(entity, pageNum, pageSize, true);
//	}
//
//	@Override
//	public PageData<T> selectPage(T entity, int pageNum, int pageSize, boolean withCount) {
//		return this.selectPage(entity, pageNum, pageSize, withCount, null);
//	}
//
//	@Override
//	public PageData<T> selectPage(T entity, int pageNum, int pageSize, boolean withCount, String order) {
//		Page<T> page = PageHelper.startPage(pageNum, pageSize, withCount);
//		StringBuilder orderByBuilder = new StringBuilder();
//		if (StringUtil.isNotBlank(order)) {
//			String[] orderArr = order.split(",");
//			for (String orderNode : orderArr) {
//				String[] orderConditionArr = orderNode.split("#");
//				String orderField = orderConditionArr[0];
//				String orderMode = orderConditionArr[1];
//				if (StringUtil.isNotBlank(orderField) && SqlUtil.isValidOrderBySql(orderField)) {
//					orderByBuilder.append(SqlUtil.camelToUnderline(orderField)).append(" ")
//							.append(Constant.DESC.equalsIgnoreCase(orderMode) ? Constant.DESC : Constant.ASC)
//							.append(",");
//				}
//			}
//			page.setOrderBy(orderByBuilder.deleteCharAt(orderByBuilder.length() - 1).toString());
//		}
//		return new PageData<>(page.doSelectPageInfo(() -> baseMapper.selectList(new QueryWrapper<>(entity))));
//	}
//
//	@Override
//	public void deleteById(Serializable id) {
//		baseMapper.deleteById(id);
//	}
//
//	@Override
//	public void deleteBatchIds(Collection<? extends Serializable> idList) {
//		// deleteByIds不支持@LogicDelete逻辑删除
//		baseMapper.deleteBatchIds(idList);
//	}

	@Override
	public PageData<T> page(PageQuery pageQuery, Wrapper<T> queryWrapper) {
		// 分页字段
		int pageNum = pageQuery != null && pageQuery.getPageNum() != null ? pageQuery.getPageNum() : 1;
		int pageSize = pageQuery != null && pageQuery.getPageSize() != null ? pageQuery.getPageSize() : 10;
		// 排序字段
		String order = pageQuery != null && StringUtil.isNotBlank(pageQuery.getOrder()) ? pageQuery.getOrder() : null;
		// 是否查count
		boolean withCount = pageQuery != null && pageQuery.getWithCount() != null ? pageQuery.getWithCount() : true;
		return this.page(queryWrapper, pageNum, pageSize, withCount, order);
	}

	@Override
	public PageData<T> page(Wrapper<T> queryWrapper, int pageNum, int pageSize) {
		return this.page(queryWrapper, pageNum, pageSize, true);
	}

	@Override
	public PageData<T> page(Wrapper<T> queryWrapper, int pageNum, int pageSize, boolean withCount) {
		return this.page(queryWrapper, pageNum, pageSize, withCount, null);
	}

	@Override
	public PageData<T> page(Wrapper<T> queryWrapper, int pageNum, int pageSize, boolean withCount, String order) {
		Page<T> page = PageHelper.startPage(pageNum, pageSize, withCount);
		StringBuilder orderByBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(order)) {
			String[] orderArr = order.split(",");
			for (String orderNode : orderArr) {
				String[] orderConditionArr = orderNode.split("#");
				String orderField = orderConditionArr[0];
				String orderMode = orderConditionArr[1];
				if (StringUtil.isNotBlank(orderField) && SqlUtil.isValidOrderBySql(orderField)) {
					orderByBuilder.append(SqlUtil.camelToUnderline(orderField)).append(" ")
							.append(Constant.DESC.equalsIgnoreCase(orderMode) ? Constant.DESC : Constant.ASC)
							.append(",");
				}
			}
			page.setOrderBy(orderByBuilder.deleteCharAt(orderByBuilder.length() - 1).toString());
		}
		return new PageData<>(page.doSelectPageInfo(() -> baseMapper.selectList(queryWrapper)));
	}
}
