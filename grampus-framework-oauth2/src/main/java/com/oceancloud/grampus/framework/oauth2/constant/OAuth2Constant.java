package com.oceancloud.grampus.framework.oauth2.constant;

/**
 * OauthConstant
 *
 * @author Beck
 * @since 2021-07-15
 */
public interface OAuth2Constant {

	String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

	String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
			+ " from oauth_client_details";

	String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

	String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";
}
