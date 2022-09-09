package com.oceancloud.grampus.framework.oauth2.modules.system.users;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 系统用户详情
 *
 * @author Beck
 * @since 2020-12-15
 */
@Data
public class SystemUserDetails implements UserDetails {
	private static final long serialVersionUID = -2384821650336456082L;
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
	 * 密码
	 */
	private String password;
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

	/**
	 * 拥有权限集合
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * authentication.getName()
	 * if (this.getPrincipal() instanceof UserDetails) {
	 *     return ((UserDetails) this.getPrincipal()).getUsername();
	 * }
	 */
	@Override
	public String getUsername() {
		return this.getUserNo();
	}

	/**
	 * DefaultPreAuthenticationChecks.
	 * void check(UserDetails user)
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * DefaultPostAuthenticationChecks.
	 * void check(UserDetails user)
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
