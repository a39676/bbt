package demo.config.costomComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import toolPack.dateTimeHandle.LocalDateTimeAdapter;

@Component
public class CustomToolBeanConfig {

	@Bean
	public LocalDateTimeAdapter getLocalDateTimeAdapter() {
		return new LocalDateTimeAdapter();
	}
}
