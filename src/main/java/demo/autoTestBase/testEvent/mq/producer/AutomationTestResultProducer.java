package demo.autoTestBase.testEvent.mq.producer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import autoTest.testEvent.pojo.constant.AutomationTestMQConstant;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import demo.baseCommon.service.CommonService;

@Component
public class AutomationTestResultProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(AutomationTestResultDTO dto) {
		if (dto == null) {
			return;
		}
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).create();
		String str = gson.toJson(dto);
		rabbitTemplate.convertAndSend(AutomationTestMQConstant.AUTOMATION_TEST_RESULT_QUEUE, str);
	}

}
