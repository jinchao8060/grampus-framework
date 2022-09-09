package com.oceancloud.grampus.framework.oauth2.support;

import com.google.common.collect.Maps;
import com.oceancloud.grampus.framework.oauth2.constant.OAuth2SecurityConstant;
import com.oceancloud.grampus.framework.oauth2.enums.SecurityModulesEnum;
import com.oceancloud.grampus.framework.oauth2.modules.system.dto.SystemUserDetailsDTO;
import com.oceancloud.grampus.framework.oauth2.modules.system.users.SystemUserDetails;
import com.oceancloud.grampus.framework.core.utils.BeanUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

/**
 * EnhancedTokenEnhanced
 *
 * @author Beck
 * @since 2021-08-13
 */
public class EnhancedTokenEnhanced implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		if(accessToken instanceof DefaultOAuth2AccessToken){
			DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
			UserDetails userDetails = (UserDetails) authentication.getUserAuthentication().getPrincipal();
			Object userDetailsDTO = null;
			String module = null;
			if (userDetails instanceof SystemUserDetails) {
				userDetailsDTO = BeanUtil.copyWithConvert(userDetails, SystemUserDetailsDTO.class);
				module = SecurityModulesEnum.SYSTEM.getModule();
			}
			Map<String, Object> info = Maps.newHashMap();
			info.put(OAuth2SecurityConstant.TOKEN_ENHANCED_USER_DETAILS, userDetailsDTO);
			info.put(OAuth2SecurityConstant.TOKEN_ENHANCED_MODULE, module);
			token.setAdditionalInformation(info);
			return token;
		}

		return accessToken;
	}
}
