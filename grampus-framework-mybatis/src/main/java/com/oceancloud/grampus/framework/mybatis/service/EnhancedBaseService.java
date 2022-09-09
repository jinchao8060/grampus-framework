package com.oceancloud.grampus.framework.mybatis.service;

import com.oceancloud.grampus.framework.mybatis.page.PageData;
import com.oceancloud.grampus.framework.mybatis.page.PageQuery;

import java.io.Serializable;
import java.util.List;

/**
 * 增强的基础服务类，简化Entity与DTO的转换
 *
 * @author Beck
 * @since 2021-04-12
 */
public interface EnhancedBaseService<T, D> extends BaseService<T> {

	/**
	 * <p>
	 * 插入一条记录（选择字段，策略插入）
	 * </p>
	 *
	 * @param dto 实体对象
	 */
	void saveOne(D dto);

	/**
	 * <p>
	 * 插入（批量），该方法不支持 Oracle、SQL Server
	 * </p>
	 *
	 * @param dtoList 实体对象集合
	 */
	void saveBatch(List<D> dtoList);

	/**
	 * <p>
	 * 插入（批量）
	 * </p>
	 *
	 * @param dtoList   实体对象集合
	 * @param batchSize 插入批次数量
	 */
	void saveBatch(List<D> dtoList, int batchSize);

	/**
	 * <p>
	 * 根据 ID 选择修改
	 * </p>
	 *
	 * @param dto 实体对象
	 */
	void modifyById(D dto);

	/**
	 * <p>
	 * 根据 ID 查询
	 * </p>
	 *
	 * @param id 主键ID
	 */
	D queryById(Serializable id);

	/**
	 * <p>
	 * 条件查询
	 * </p>
	 *
	 * @param dto 实体
	 * @return 查询结果
	 */
	D queryOne(D dto);

	/**
	 * <p>
	 * 条件查询
	 * </p>
	 *
	 * @param dto 实体
	 * @return 查询结果
	 */
	List<D> queryList(D dto);

//	/**
//	 * <p>
//	 * 条件查询
//	 * </p>
//	 *
//	 * @param params 参数
//	 * @return 查询结果
//	 */
//	List<D> queryList(Map<String, Object> params);

	/**
	 * <p>
	 * 查询所有
	 * </p>
	 *
	 * @return 查询结果
	 */
	List<D> queryAll();

	/**
	 * <p>
	 * 查询数据量
	 * </p>
	 *
	 * @return 查询结果
	 */
	long queryCount(D dto);
//
//	/**
//	 * <p>
//	 * 分页条件查询
//	 * </p>
//	 *
//	 * @param pageQuery   分页参数
//	 * @param entityQuery 查询条件映射实体
//	 * @return 查询结果
//	 */
//	PageData<D> queryPage(PageQuery pageQuery, Object entityQuery);
//
//	/**
//	 * <p>
//	 * 分页条件查询
//	 * </p>
//	 *
//	 * @param params 参数
//	 * @return 查询结果
//	 */
//	PageData<D> queryPage(Map<String, Object> params);
//
//	/**
//	 * <p>
//	 * 分页条件查询 (默认查count)
//	 * </p>
//	 *
//	 * @param dto      查询实例
//	 * @param pageNum  页码
//	 * @param pageSize 每页数据量
//	 * @return 查询结果
//	 */
//	PageData<D> queryPage(D dto, int pageNum, int pageSize);
//
//	/**
//	 * <p>
//	 * 分页条件查询
//	 * </p>
//	 *
//	 * @param dto       查询实例
//	 * @param pageNum   页码
//	 * @param pageSize  每页数据量
//	 * @param withCount 是否查count(true查count false)
//	 * @return 查询结果
//	 */
//	PageData<D> queryPage(D dto, int pageNum, int pageSize, boolean withCount);
//
//	/**
//	 * <p>
//	 * 分页条件查询
//	 * </p>
//	 *
//	 * @param dto      查询实例
//	 * @param pageNum  页码
//	 * @param pageSize 每页数据量
//	 * @param order    排序方式(field1&asc,field2&desc)
//	 * @return 查询结果
//	 */
//	PageData<D> queryPage(D dto, int pageNum, int pageSize, boolean withCount, String order);

	/**
	 * <p>
	 * 分页条件查询
	 * </p>
	 *
	 * @param pageQuery   分页参数
	 * @param entityQuery 查询条件映射实体
	 * @return 查询结果
	 */
	PageData<D> queryPage(PageQuery pageQuery, Object entityQuery);

	/**
	 * <p>
	 * 分页条件查询 (默认查count)
	 * </p>
	 *
	 * @param dto      查询实例
	 * @param pageNum  页码
	 * @param pageSize 每页数据量
	 * @return 查询结果
	 */
	PageData<D> queryPage(D dto, int pageNum, int pageSize);

	/**
	 * <p>
	 * 分页条件查询
	 * </p>
	 *
	 * @param dto       查询实例
	 * @param pageNum   页码
	 * @param pageSize  每页数据量
	 * @param withCount 是否查count(true查count false)
	 * @return 查询结果
	 */
	PageData<D> queryPage(D dto, int pageNum, int pageSize, boolean withCount);

	/**
	 * <p>
	 * 分页条件查询
	 * </p>
	 *
	 * @param dto       查询实例
	 * @param pageNum   页码
	 * @param pageSize  每页数据量
	 * @param withCount 是否查询数据量
	 * @param order     排序方式(field1&asc,field2&desc)
	 * @return 查询结果
	 */
	PageData<D> queryPage(D dto, int pageNum, int pageSize, boolean withCount, String order);
}
