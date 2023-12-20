package demo.config.costomComponent;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import toolPack.dateTimeHandle.LocalDateTimeHandler;

@Component
public class BbtDynamicKey {

	@Autowired
	private CustomPasswordEncoder passwordEncoder;
	@Autowired
	private LocalDateTimeHandler localDateTimeHandler;

	private static final String SALT = "BBT";
	private static final String KEY_FORMAT = SALT + "_";

	public String createKey(LocalDateTime time) {
		if (time == null) {
			time = LocalDateTime.now().plusSeconds(3);
		}
		String nowStr = localDateTimeHandler.dateToStr(time);
		String keyStr = KEY_FORMAT + nowStr;
		String keyEncode = passwordEncoder.encode(SALT, keyStr);
		return keyEncode;
	}

	public String createKey() {
		return createKey(LocalDateTime.now());
	}
}
