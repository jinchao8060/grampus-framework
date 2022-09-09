package com.oceancloud.grampus.framework.core.constant;

/**
 * 常量
 *
 * @author Beck
 * @since 2021-01-21
 */
public interface Constant {
	/**
	 * 成功
	 */
	int SUCCESS = 1;
	/**
	 * 失败
	 */
	int FAIL = 0;
	/**
	 * OK
	 */
	String OK = "OK";
	/**
	 * 用户标识
	 */
	String USER_KEY = "userId";
	/**
	 * 菜单根节点标识
	 */
	Long MENU_ROOT = 0L;
	/**
	 * 部门根节点标识
	 */
	Long DEPT_ROOT = 0L;
	/**
	 * 数据字典根节点标识
	 */
	Long DICT_ROOT = 0L;
	/**
	 * 升序
	 */
	String ASC = "asc";
	/**
	 * 降序
	 */
	String DESC = "desc";
	/**
	 * 删除字段名
	 */
	String DEL_FLAG = "del_flag";
	/**
	 * 创建时间字段名
	 */
	String CREATE_DATE = "create_date";

	/**
	 * 当前页码
	 */
	String PAGE_NUM = "pageNum";
	/**
	 * 每页显示记录数
	 */
	String PAGE_SIZE = "pageSize";
	/**
	 * 排序方式
	 */
	String ORDER = "order";
	/**
	 * 是否查count
	 */
	String WITH_COUNT = "withCount";
	/**
	 * token header
	 */
	String AUTHORIZATION_HEADER = "Authorization";
}