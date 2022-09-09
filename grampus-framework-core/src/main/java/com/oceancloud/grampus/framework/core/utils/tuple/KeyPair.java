package com.oceancloud.grampus.framework.core.utils.tuple;

import com.oceancloud.grampus.framework.core.utils.crypto.RSAUtil;
import lombok.AllArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * rsa 的 key pair 封装
 *
 * @author Beck
 */
@AllArgsConstructor
public class KeyPair {
	private final java.security.KeyPair keyPair;

	public PublicKey getPublic() {
		return keyPair.getPublic();
	}

	public PrivateKey getPrivate() {
		return keyPair.getPrivate();
	}

	public byte[] getPublicBytes() {
		return this.getPublic().getEncoded();
	}

	public byte[] getPrivateBytes() {
		return this.getPrivate().getEncoded();
	}

	public String getPublicBase64() {
		return RSAUtil.getKeyString(this.getPublic());
	}

	public String getPrivateBase64() {
		return RSAUtil.getKeyString(this.getPrivate());
	}

	@Override
	public String toString() {
		return "PublicKey=" + this.getPublicBase64() + '\n' + "PrivateKey=" + this.getPrivateBase64();
	}
}
