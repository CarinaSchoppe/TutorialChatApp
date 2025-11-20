package utility;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Utility {

  private static final String KEY = "0123456789ABCDEF";

  private static SecretKeySpec getKey() {
    return new SecretKeySpec(KEY.getBytes(), "AES");
  }

  public static String encrypt(String plain)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, getKey());
    return Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes()));
  }

  public static String decrypt(String encrypted)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, getKey());
    return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
  }

}