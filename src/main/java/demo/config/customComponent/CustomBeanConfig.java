package demo.config.customComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import mail.service.MailToolService;
import toolPack.dateTimeHandle.DateHandler;
import toolPack.dateTimeHandle.LocalDateTimeHandler;
import toolPack.httpHandel.HttpUtil;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.numericHandel.NumericUtilCustom;
import toolPack.stringHandle.StringUtilCustom;

@Component
public class CustomBeanConfig {

	@Bean
	public BaseUtilCustom getBaseUtilCustom() {
		return new BaseUtilCustom();
	}
	
	@Bean
	public SnowFlake getSnowFlake() {
		return new SnowFlake();
	}

	@Bean
	public StringUtilCustom getStringUtilCustom() {
		return new StringUtilCustom();
	}
	
	@Bean
	public FileUtilCustom getFileUtilCustom() {
		return new FileUtilCustom();
	}
	
	@Bean
	public NumericUtilCustom getNumericUtilCustom() {
		return new NumericUtilCustom();
	}
	
	@Bean
	public HttpUtil getHttpUtil() {
		return new HttpUtil();
	}
	
	@Bean
	public DateHandler getDateHandler() {
		return new DateHandler();
	}
	
	@Bean
	public LocalDateTimeHandler getLocalDateTimeHandler() {
		return new LocalDateTimeHandler();
	}
	
	@Bean
	public MailToolService mailToolService() {
		return new MailToolService();
	}
}
