package demo.task.service.impl.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import autoTest.testEvent.common.pojo.constant.AutomationTestMQConstant;
import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;

@Component
public class TestEventInsertAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(AutomationTestInsertEventDTO dto) {
		if (dto == null) {
			return;
		}
		JSONObject json = JSONObject.fromObject(dto);
		json.put("appointment", localDateTimeHandler.dateToStr(dto.getAppointment()));
		rabbitTemplate.convertAndSend(AutomationTestMQConstant.TEST_EVENT_INSERT_QUEUE, json.toString());
	}

}