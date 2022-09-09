package com.oceancloud.grampus.framework.signin.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.oceancloud.grampus.framework.core.utils.Exceptions;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.core.utils.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;

/**
 * AppleIdTokenUtil
 *
 * @author Beck
 * @since 2022-06-30
 */
@Slf4j
@UtilityClass
public class AppleIdTokenUtil {

	public String getHeaderField(String jwt, String field) {
		if (jwt.split("\\.").length <= 1) {
			return null;
		}
		String header = new String(Base64.decodeBase64(jwt.split("\\.")[0]), StandardCharsets.UTF_8);
		JsonNode jsonNode = JSONUtil.readTree(header);
		return jsonNode.get(field).asText();
	}

	public PublicKey buildPublicKey(String n, String e) {
		BigInteger modulus = new BigInteger(1, Base64.decodeBase64(n));
		BigInteger publicExponent = new BigInteger(1, Base64.decodeBase64(e));
		try {
			RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePublic(spec);
		} catch (Exception ex) {
			log.error("AppleIdTokenUtil.buildPublicKey generatePublic error.", ex);
		}
		return null;
	}

	public Claims parseClaims(String idToken, PublicKey publicKey) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(publicKey).build()
					.parseClaimsJws(idToken)
					.getBody();
		} catch (Exception e) {
			log.error("AppleIdTokenUtil.parseClaimsJws error.", e);
		}
		return null;
	}

	public String genClientSecret(String appId, String teamId, String keyId, String privateKey) {
		PrivateKey key = buildPrivateKey(privateKey);

		Date now = new Date();

		return Jwts.builder()
				.setHeaderParam("kid", keyId)
				.setSubject(appId)
				.setIssuedAt(now)
				.setExpiration(DateUtil.plusDays(now, 30))
				.setAudience("https://appleid.apple.com")
				.setIssuer(teamId)
				.signWith(key, SignatureAlgorithm.ES256)
				.compact();
	}

	private PrivateKey buildPrivateKey(String privateKeyContent) {
		try {
			return KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(
					Base64.decodeBase64(privateKeyContent)));
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			throw Exceptions.unchecked(e);
		}
	}
}
