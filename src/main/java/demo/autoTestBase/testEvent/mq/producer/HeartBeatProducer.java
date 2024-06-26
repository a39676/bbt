package demo.autoTestBase.testEvent.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auxiliaryCommon.pojo.constant.ServiceMQConstant;
import auxiliaryCommon.pojo.type.HeartBeatType;
import demo.baseCommon.service.CommonService;

@Component
public class HeartBeatProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send() {
		rabbitTemplate.convertAndSend(ServiceMQConstant.HEART_BEAT, HeartBeatType.BBT.getName());
		rabbitTemplate.convertAndSend(ServiceMQConstant.HEART_BEAT, HeartBeatType.WORKER1.getName());
	}

}
