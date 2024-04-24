package demo.config.customComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import finance.common.tool.KLineToolUnit;
import toolPack.dateTimeHandle.LocalDateTimeAdapter;

@Component
public class CustomToolBeanConfig {

	@Bean
	public LocalDateTimeAdapter getLocalDateTimeAdapter() {
		return new LocalDateTimeAdapter();
	}
	
	@Bean
	public CustomPasswordEncoder getCustomPasswordEncoder() {
		return new CustomPasswordEncoder();
	}
	
	@Bean
	public KLineToolUnit getKLineToolUnit() {
		return new KLineToolUnit();
	}
}
