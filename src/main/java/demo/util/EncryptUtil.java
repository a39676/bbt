package demo.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class EncryptUtil {
	
	public static String Sha1(String str) {
		return DigestUtils.sha1Hex(str);
	}
	
	public static byte[] ToMd5ByteArray(String str) {
		
		try {
			byte[] bytesOfMessage = str.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(bytesOfMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String ToMd5String(String str) { 
		if(ToMd5ByteArray(str) == null || ToMd5ByteArray(str).length == 0 ) {
			return null;
		}
		try {
			return new String(ToMd5ByteArray(str));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String aesEncrypt(String key, String initVector, String value) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException {
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		
		byte[] encrypted = cipher.doFinal(value.getBytes());
		
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static String aesDecrypt(String key, String initVector, String encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		
		byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
		
		return new String(original);
	}
	
	public static String customEncrypt(List<List<Character>> keys, String inputStr) {
		StringBuffer builder = new StringBuffer();
		int keyIndex = 0;
		for(int i = 0; i < inputStr.length(); i++) {
			if(i == inputStr.length() - 1) {
				builder.append(inputStr.charAt(0));
				keyIndex = Integer.parseInt(String.valueOf(inputStr.charAt(0)));
				builder.append(keys.get(keyIndex).get(Integer.parseInt(String.valueOf(inputStr.charAt(i)))));
			} else {
				builder.append(inputStr.charAt(i + 1));
				keyIndex = Integer.parseInt(String.valueOf(inputStr.charAt(i + 1)));
				builder.append(keys.get(keyIndex).get(Integer.parseInt(String.valueOf(inputStr.charAt(i)))));
			}
		}
		
		for(int i = 0; i < builder.length(); i = i + 2) {
			builder.replace(i, i+1, keys.get(0).get(Integer.parseInt(String.valueOf(builder.charAt(i)))).toString());
		}
		
		return builder.toString();
	}
	
	public static String customDecrypt(List<List<Character>> keys, String inputStr) {
		if(keys == null || keys.size() < 1 || StringUtils.isBlank(inputStr) || inputStr.length() % 2 != 0) {
			return null;
		}
		
		if(!allInKeys(keys, inputStr)) {
			return null;
		}
		
		StringBuffer builder = new StringBuffer(inputStr);
		int keyIndex = 0;
		String tmpChar = null;
		for(int i = 0; i < builder.length(); i = i + 2) {
			tmpChar = String.valueOf(keys.get(0).indexOf((builder.charAt(i))));
			builder.replace(i, i+1, tmpChar);
		}
		
		StringBuffer resultBuilder = new StringBuffer();
		for(int i = 0; i < builder.length(); i = i + 2) {
			keyIndex = Integer.parseInt(String.valueOf(builder.charAt(i)));
			resultBuilder.append(keys.get(keyIndex).indexOf(builder.charAt(i + 1)));
		}
		
		return resultBuilder.toString();
	}
	
	private static boolean allInKeys(List<List<Character>> keys, String inputStr) {
		if(keys == null || keys.size() < 1 || StringUtils.isBlank(inputStr)) {
			return false;
		}
		
		List<String> keywords = new ArrayList<String>();
		for(int i = 0; i < keys.size(); i++) {
			for(int j = 0; j < keys.get(i).size(); j++) {
				keywords.add(keys.get(i).get(j).toString());
			}
		}
		
		for(int i = 0; i < inputStr.length(); i++) {
			if(!keywords.contains(String.valueOf(inputStr.charAt(i)))) {
				return false;
			}
		}
		
		return true;
		
	}

}
