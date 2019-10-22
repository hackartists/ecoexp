package ecoexp.common.utils;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;
import ecoexp.common.exception.EcoException;
import ecoexp.common.response.ErrorCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CryptoUtil {
	private static Logger logger = LoggerFactory.getLogger(CryptoUtil.class);

	private static final String key = "1234567890123456";
	private static final String initVector = "0987654321098765";

	public static String encrypt(String plain) throws EcoException {
		logger.debug("In: encrypt({})", plain);
		String res = "";

		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(plain.getBytes());
			res = Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new EcoException(ErrorCode.EncryptFailedErrorCode,e);
		}

		logger.debug("Out: encrypt");
		return res;
	}

	public static String decrypt(String encrypted)  throws EcoException  {
		logger.debug("In: decrypt({})", encrypted);
		String res="";

		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

			res=  new String(original);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new EcoException(ErrorCode.DecryptFailedErrorCode,e);
		}

		logger.debug("Out: decrypt");

		return res;
	}
}
