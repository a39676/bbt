package demo.autoTestBase.testEvent.mq.receive;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import autoTest.testEvent.common.pojo.constant.AutomationTestMQConstant;
import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;

@Component
@RabbitListener(queues = AutomationTestMQConstant.TEST_EVENT_INSERT_QUEUE)
public class TestEventInsertQueueAckReceiver extends CommonService {

	@Autowired
	private TestEventService testEventService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {

		try {
			AutomationTestInsertEventDTO dto = msgToAutomationTestInsertEventDTO(messageStr);

			if (dto != null) {
				testEventService.reciveTestEventAndRun(dto);
			}

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {

			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}

	}

	private AutomationTestInsertEventDTO msgToAutomationTestInsertEventDTO(String msgStr) {
		try {
			JSONObject json = JSONObject.fromObject(msgStr);
			AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
			
			if(json.has("appointment")) {
				dto.setAppointment(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(json.getString("appointment")));
			}
			if(json.has("testEventId")) {
				dto.setTestEventId(json.getLong("testEventId"));
			}
			if(json.has("testModuleType")) {
				dto.setTestModuleType(json.getLong("testModuleType"));
			}
			if(json.has("flowType")) {
				dto.setFlowType(json.getLong("flowType"));
			}
			if(json.has("paramStr")) {
				dto.setParamStr(json.getString("paramStr"));
			}
			
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
