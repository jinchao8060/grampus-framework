package com.oceancloud.grampus.framework.oauth2.support;

import com.oceancloud.grampus.framework.oauth2.constant.OAuth2SecurityConstant;
import com.oceancloud.grampus.framework.oauth2.enums.SecurityModulesEnum;
import com.oceancloud.grampus.framework.oauth2.modules.system.users.SystemUserDetails;
import com.oceancloud.grampus.framework.core.utils.BeanUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * EnhancedUserAuthenticationConverter
 * 调用/oauth/check_token后转换user时只使用了user_name字段，导致principal数据为user_name
 * 重写extractAuthentication逻辑使principal能获取到userDetails数据
 *
 * @author Beck
 * @since 2021-08-14
 */
public class EnhancedUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		if (map.containsKey(USERNAME)) {
			Object principal = map.get(USERNAME);
			Object userDetails = map.get(OAuth2SecurityConstant.TOKEN_ENHANCED_USER_DETAILS);
			String module = (String) map.get(OAuth2SecurityConstant.TOKEN_ENHANCED_MODULE);

			if (SecurityModulesEnum.SYSTEM.equals(SecurityModulesEnum.convert(module))) {
				principal = BeanUtil.toBean((Map<String, Object>) userDetails, SystemUserDetails.class, true);
			}

			Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
			return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		if (!map.containsKey(AUTHORITIES)) {
			return null;
		}
		Object authorities = map.get(AUTHORITIES);
		if (authorities instanceof String) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
		}
		if (authorities instanceof Collection) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
					.collectionToCommaDelimitedString((Collection<?>) authorities));
		}
		throw new IllegalArgumentException("Authorities must be either a String or a Collection");
	}
}
