package demo.autoTestBase.testEvent.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import autoTest.testEvent.pojo.constant.AutomationTestResultMQConstant;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Component
public class AutomationTestResultProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(AutomationTestResultDTO dto) {
		if (dto == null) {
			return;
		}
		JSONObject json = (JSONObject) JSONSerializer.toJSON(dto);
		rabbitTemplate.convertAndSend(AutomationTestResultMQConstant.AUTOMATION_TEST_RESULT_QUEUE, json.toString());
	}

}
