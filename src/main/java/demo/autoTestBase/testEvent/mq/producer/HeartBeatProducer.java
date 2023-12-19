// 2023-12-19 NOT use when deploy on local service

//package demo.autoTestBase.testEvent.mq.producer;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import autoTest.testEvent.common.pojo.constant.AutomationTestMQConstant;
//import demo.baseCommon.service.CommonService;
//
//@Component
//public class HeartBeatProducer extends CommonService {
//
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
//	public void send() {
//		rabbitTemplate.convertAndSend(AutomationTestMQConstant.HEART_BEAT, "_");
//	}
//
//}
