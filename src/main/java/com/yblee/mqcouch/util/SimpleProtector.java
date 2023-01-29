package com.yblee.mqcouch.util;

import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;


public class SimpleProtector {

	private static final String ALGORITHM = "AES";
	// length shoud be 16
	private static final byte[] keyValue = 
			new byte[] { 'X', 'r', 'm', 'i', 'K', 'e', 'v', 'l', 'W', 'n', 'i', 'z', 'O', 's', 'D', 'B' };

  public static String encrypt(String valueToEnc) throws Exception {
    Key key = generateKey();
    Cipher c = Cipher.getInstance(ALGORITHM);
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[] encValue = c.doFinal(valueToEnc.getBytes());
    String encryptedValue = new String(Base64.encodeBase64(encValue));
    return encryptedValue;
  }

  public static String decrypt(String encryptedValue) throws Exception {
      Key key = generateKey();
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(Cipher.DECRYPT_MODE, key);
      byte[] decordedValue = Base64.decodeBase64(encryptedValue.getBytes());
      byte[] decValue = c.doFinal(decordedValue);
      String decryptedValue = new String(decValue);
      return decryptedValue;
  }

  private static Key generateKey() throws Exception {
      Key key = new SecretKeySpec(keyValue, ALGORITHM);
      return key;
  }
}
