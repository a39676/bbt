//package demo.config;
//
/*
 * 2019-05-25
 * 项目引用jar包转换---->从原配springFramework 转换到 springBoot
 * socket通讯相关暂时注释
 * socket dev mark
 */
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//
//	@Override
//	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//		messages
//			.simpDestMatchers("/topic/**").authenticated()
//			.anyMessage().authenticated();
//	}
//
//	@Override
//	protected boolean sameOriginDisabled() {
//	    return true;
//	}
//	
//	// TODO
//	// https://www.baeldung.com/spring-security-websockets
//	// 正在尝试 webSocket通讯
//}
