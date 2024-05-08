package demo.config.customComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import demo.baseCommon.pojo.constant.SystemConstant;

@Configuration
public class ConfigFromOutSourceProperties {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
		properties.setLocation(new FileSystemResource(SystemConstant.ROOT_PATH + "/optionFile/config.properties"));
		properties.setIgnoreResourceNotFound(false);
		return properties;
	}

}