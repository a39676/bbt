package demo.autoTestBase.testEvent.mq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import autoTest.testEvent.pojo.constant.TestEventMQConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;

@Component
@RabbitListener(queues = TestEventMQConstant.TEST_EVENT_EXECUTE_QUEUE)
public class TestEventExecuteQueueAckReceiver extends CommonService {

	@Autowired
	private TestEventService testEventService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {

		try {
			TestEvent te = msgToTestEventPO(messageStr);

			if (te != null) {
				testEventService.reciveTestEventAndRun(te);
			}

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {

			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}

	}

	private TestEvent msgToTestEventPO(String msgStr) {
		try {
			JSONObject json = JSONObject.fromObject(msgStr);
			TestEvent te = new TestEvent();
			
			if(json.has("id")) {
				te.setId(json.getLong("id"));
			}
			if(json.has("projectId")) {
				te.setProjectId(json.getLong("projectId"));
			}
			if(json.has("flowId")) {
				te.setFlowId(json.getLong("flowId"));
			}
			if(json.has("processId")) {
				te.setProcessId(json.getLong("processId"));
			}
			if(json.has("moduleId")) {
				te.setModuleId(json.getLong("moduleId"));
			}
			if(json.has("eventName")) {
				te.setEventName(json.getString("eventName"));
			}
			if(json.has("remark")) {
				te.setRemark(json.getString("remark"));
			}
			if(json.has("parameterFilePath")) {
				te.setParameterFilePath(json.getString("parameterFilePath"));
			}
			if(json.has("reportPath")) {
				te.setReportPath(json.getString("reportPath"));
			}
			if(json.has("createTime")) {
				te.setCreateTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(json.getString("createTime")));
			}
			if(json.has("appointment")) {
				te.setAppointment(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(json.getString("appointment")));
			}
			if(json.has("startTime")) {
				te.setStartTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(json.getString("startTime")));
			}
			if(json.has("endTime")) {
				te.setEndTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(json.getString("endTime")));
			}
			if(json.has("isPass")) {
				if("true".equals(json.getString("isPass"))) {
					te.setIsPass(true);
				} else {
					te.setIsPass(false);
				}
			}
			if(json.has("isDelete")) {
				if("true".equals(json.getString("isDelete"))) {
					te.setIsDelete(true);
				} else {
					te.setIsDelete(false);
				}
			}
			
			return te;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
