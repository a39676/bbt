package demo.config.customComponent;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

public class CustomPasswordEncoder {

	public String encode(String salt, CharSequence rawPassword) {
		if(StringUtils.isBlank(salt)) {
			salt = "demoSalt";
		}

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			byte[] tmpByteArray = digest.digest((rawPassword + salt).getBytes(StandardCharsets.UTF_8));
			
			String tmpStr = Base64.getEncoder().encodeToString(tmpByteArray);
			
			return tmpStr;
			
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	public String encode(CharSequence rawPassword) {
		return encode(null, rawPassword);
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		if (encodedPassword == null || encodedPassword.length() == 0) {
			return false;
		}

		return encode(rawPassword).equals(encodedPassword); 
	}
	
	public boolean matches(CharSequence rawPassword, String encodedPassword, String salt) {
		
		if (encodedPassword == null || encodedPassword.length() == 0) {
			return false;
		}

		return encode(salt, rawPassword).equals(encodedPassword); 
	}
}