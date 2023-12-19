package demo.autoTestBase.testEvent.mq.producer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import autoTest.testEvent.common.pojo.constant.AutomationTestMQConstant;
import autoTest.testEvent.common.pojo.dto.AutomationTestResultDTO;
import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.service.CommonService;

@Component
public class AutomationTestResultProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private SystemOptionService systemOptionService;

	public void send(AutomationTestResultDTO dto) {
		if (dto == null) {
			return;
		}
		if (systemOptionService.isRaspberry()) {
			log.error("Automation test result: " + dto.toString());
		}
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).create();
		String str = gson.toJson(dto);
		rabbitTemplate.convertAndSend(AutomationTestMQConstant.AUTOMATION_TEST_RESULT_QUEUE, str);
	}

}
