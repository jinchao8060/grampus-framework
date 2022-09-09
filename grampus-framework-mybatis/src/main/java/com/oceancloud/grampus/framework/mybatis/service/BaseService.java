package com.oceancloud.grampus.framework.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oceancloud.grampus.framework.mybatis.page.PageData;
import com.oceancloud.grampus.framework.mybatis.page.PageQuery;

/**
 * 基础服务接口，所有Service接口都要继承(继承后即可获得BaseMapper的CRUD功能)
 * Project: grampus
 *
 * @author Beck
 * @since 2020-12-02
 */
public interface BaseService<T> extends IService<T> {

//	/**
//	 * <p>
//	 * 插入一条记录（选择字段，策略插入）
//	 * </p>
//	 *
//	 * @param entity 实体对象
//	 */
//	void insert(T entity);
//
//	/**
//	 * <p>
//	 * 插入（批量），该方法不支持 Oracle、SQL Server
//	 * </p>
//	 *
//	 * @param entityList 实体对象集合
//	 */
//	void insertBatch(List<T> entityList);
//
//	/**
//	 * <p>
//	 * 插入（批量）
//	 * </p>
//	 *
//	 * @param entityList 实体对象集合
//	 * @param batchSize  插入批次数量
//	 */
//	void insertBatch(List<T> entityList, int batchSize);
//
//	/**
//	 * <p>
//	 * 根据 ID 选择修改
//	 * </p>
//	 *
//	 * @param entity 实体对象
//	 */
//	void updateById(T entity);
//
//	/**
//	 * <p>
//	 * 根据 whereEntity 条件，更新记录
//	 * </p>
//	 *
//	 * @param entity  实体对象
//	 * @param wrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.Wrapper}
//	 */
//	void update(T entity, Wrapper<T> wrapper);
//
//	/**
//	 * <p>
//	 * 根据ID 批量更新
//	 * </p>
//	 *
//	 * @param entityList 实体对象集合
//	 */
//	void updateBatchById(List<T> entityList);
//
//	/**
//	 * <p>
//	 * 根据ID 批量更新
//	 * </p>
//	 *
//	 * @param entityList 实体对象集合
//	 * @param batchSize  更新批次数量
//	 */
//	void updateBatchById(List<T> entityList, int batchSize);
//
//	/**
//	 * <p>
//	 * 根据 ID 查询
//	 * </p>
//	 *
//	 * @param id 主键ID
//	 */
//	T selectById(Serializable id);
//
//	/**
//	 * <p>
//	 * 条件查询
//	 * </p>
//	 *
//	 * @param entity 实体
//	 * @return 查询结果
//	 */
//	T selectOne(T entity);
//
//	/**
//	 * <p>
//	 * 条件查询
//	 * </p>
//	 *
//	 * @param entity 实体
//	 * @return 查询结果
//	 */
//	List<T> selectList(T entity);
//
//	/**
//	 * <p>
//	 * 条件查询
//	 * </p>
//	 *
//	 * @param params 参数
//	 * @param clazz  查询条件映射实体的类名
//	 * @return 查询结果
//	 */
//	List<T> selectList(Map<String, Object> params, Class<T> clazz);
//
//	/**
//	 * <p>
//	 * 查询所有
//	 * </p>
//	 *
//	 * @return 查询结果
//	 */
//	List<T> selectAll();
//
//	/**
//	 * <p>
//	 * 查询数据量
//	 * </p>
//	 *
//	 * @return 查询结果
//	 */
//	long selectCount(T entity);
//
//	/**
//	 * <p>
//	 * 分页条件查询
//	 * </p>
//	 *
//	 * @param pageQuery   分页参数
//	 * @param entityQuery 查询条件映射实体
//	 * @param clazz       查询条件映射实体的类名
//	 * @return 查询结果
//	 */
//	PageData<T> selectPage(PageQuery pageQuery, Object entityQuery, Class<T> clazz);
//
//	/**
//	 * <p>
//	 * 分页条件查询
//	 * </p>
//	 *
//	 * @param params 参数
//	 * @param clazz  查询条件映射实体的类名
//	 * @return 查询结果
//	 */
//	PageData<T> selectPage(Map<String, Object> params, Class<T> clazz);
//
//	/**
//	 * <p>
//	 * 分页条件查询 (默认查count)
//	 * </p>
//	 *
//	 * @param entity   查询实例
//	 * @param pageNum  页码
//	 * @param pageSize 每页数据量
//	 * @return 查询结果
//	 */
//	PageData<T> selectPage(T entity, int pageNum, int pageSize);
//
//	/**
//	 * <p>
//	 * 分页条件查询
//	 * </p>
//	 *
//	 * @param entity    查询实例
//	 * @param pageNum   页码
//	 * @param pageSize  每页数据量
//	 * @param withCount 是否查count(true查count false)
//	 * @return 查询结果
//	 */
//	PageData<T> selectPage(T entity, int pageNum, int pageSize, boolean withCount);
//
//	/**
//	 * <p>
//	 * 分页条件查询
//	 * </p>
//	 *
//	 * @param entity   查询实例
//	 * @param pageNum  页码
//	 * @param pageSize 每页数据量
//	 * @param order    排序字段以及方式(field1&asc,field2&desc)
//	 * @return 查询结果
//	 */
//	PageData<T> selectPage(T entity, int pageNum, int pageSize, boolean withCount, String order);
//
//	/**
//	 * <p>
//	 * 根据 ID 删除（支持@LogicDelete注解进行逻辑删除）
//	 * </p>
//	 *
//	 * @param id 主键ID
//	 */
//	void deleteById(Serializable id);
//
//	/**
//	 * <p>
//	 * 删除（根据ID 批量删除，支持@LogicDelete注解进行逻辑删除）
//	 * </p>
//	 *
//	 * @param idList 主键ID列表
//	 */
//	void deleteBatchIds(Collection<? extends Serializable> idList);

	/**
	 * <p>
	 * 分页条件查询
	 * </p>
	 *
	 * @param pageQuery    分页参数
	 * @param queryWrapper 实体对象封装操作类 com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
	 * @return 查询结果
	 */
	PageData<T> page(PageQuery pageQuery, Wrapper<T> queryWrapper);

	/**
	 * <p>
	 * 分页条件查询 (默认查count)
	 * </p>
	 *
	 * @param queryWrapper 实体对象封装操作类 com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
	 * @param pageNum      页码
	 * @param pageSize     每页数据量
	 * @return 查询结果
	 */
	PageData<T> page(Wrapper<T> queryWrapper, int pageNum, int pageSize);

	/**
	 * <p>
	 * 分页条件查询
	 * </p>
	 *
	 * @param queryWrapper 实体对象封装操作类 com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
	 * @param pageNum      页码
	 * @param pageSize     每页数据量
	 * @param withCount    是否查count(true查count false)
	 * @return 查询结果
	 */
	PageData<T> page(Wrapper<T> queryWrapper, int pageNum, int pageSize, boolean withCount);

	/**
	 * <p>
	 * 分页条件查询
	 * </p>
	 *
	 * @param queryWrapper 实体对象封装操作类 com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
	 * @param pageNum      页码
	 * @param pageSize     每页数据量
	 * @param order        排序字段以及方式(field1&asc,field2&desc)
	 * @return 查询结果
	 */
	PageData<T> page(Wrapper<T> queryWrapper, int pageNum, int pageSize, boolean withCount, String order);
}
