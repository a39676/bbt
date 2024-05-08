package demo.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RabbitMqConfig {

	@Value("${rabbitMQ.username}")
	private String rabbitMqUsername;
	@Value("${rabbitMQ.password}")
	private String rabbitMqPassword;
	@Value("${rabbitMQ.host}")
	private String rabbitMqHost;
	@Value("${rabbitMQ.port}")
	private Integer rabbitMqPort;
	@Value("${rabbitMQ.virtualHost}")
	private String rabbitMqVirtualHost;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	// 从外部properties获取变量运行顺序后于spring boot init rabbitMQ
	// 因此从此处update Connection
	@PostConstruct
	public void rabbitTemplateUpdateConnectionFactory() {
		rabbitTemplate.setConnectionFactory(createRabbitMqCachingConnectionFactory());
	}

	// 从外部properties获取变量运行顺序后于spring boot init rabbitMQ
	// 因此从此处update Connection
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(createRabbitMqCachingConnectionFactory());
		factory.setConcurrentConsumers(1);
		factory.setMaxConcurrentConsumers(1);
		return factory;
	}

	private CachingConnectionFactory createRabbitMqCachingConnectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setUsername(rabbitMqUsername);
		cachingConnectionFactory.setPassword(rabbitMqPassword);
		cachingConnectionFactory.setHost(rabbitMqHost);
		cachingConnectionFactory.setPort(rabbitMqPort);
		cachingConnectionFactory.setVirtualHost(rabbitMqVirtualHost);
		return cachingConnectionFactory;
	}
}
