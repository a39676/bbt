package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.config.costomComponent.TerminateBean;

@Configuration
public class ShutdownConfig {

	@Bean
	public TerminateBean getTerminateBean() {
		return new TerminateBean();
	}
}
