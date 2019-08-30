//package demo.config;
//
/*
 * 2019-05-25
 * 项目引用jar包转换---->从原配springFramework 转换到 springBoot
 * socket通讯相关暂时注释
 * socket dev mark
 */
//import java.util.List;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.converter.MessageConverter;
//import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
//import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class SocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
//
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry config) {
//		config.enableSimpleBroker("/topic");
//		config.setApplicationDestinationPrefixes("/app");
//	}
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry
//			.addEndpoint("/topic/public", "/topic/single")
////			.setAllowedOrigins("*")  // https://blog.csdn.net/qq_35638499/article/details/80928356
//			.withSockJS()
//		;
//	}
//
//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void configureClientInboundChannel(ChannelRegistration arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void configureClientOutboundChannel(ChannelRegistration arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean configureMessageConverters(List<MessageConverter> arg0) {
////		arg0.add(new CustomMessageConverter());
//		return true;
//	}
//
//	@Override
//	public void configureWebSocketTransport(WebSocketTransportRegistration arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//}