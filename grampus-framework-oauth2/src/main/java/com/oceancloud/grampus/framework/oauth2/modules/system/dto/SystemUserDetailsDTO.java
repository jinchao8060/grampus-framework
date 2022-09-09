package com.oceancloud.grampus.framework.oauth2.modules.system.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统会员详情DTO (相对SystemUserDetails省略了password字段，防止/oauth/token接口泄漏password)
 *
 * @author Beck
 * @since 2021-05-07
 */
@ApiModel("系统用户详情")
@Data
public class SystemUserDetailsDTO implements Serializable {
	private static final long serialVersionUID = 6809412036525186257L;
	/**
	 * 用户ID
	 */
	private Long id;
	/**
	 * 员工号
	 */
	private String userNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 所属角色ID
	 */
	private List<Long> roleIds;
	/**
	 * 所属部门ID
	 */
	private Long deptId;
	/**
	 * 是否超级管理员(0普通 1超管)
	 */
	private Integer superAdmin;
	/**
	 * 是否启用(0停用 1启用)
	 */
	private Integer status;
	/**
	 * 拥有权限
	 */
	private String permissions;
}
