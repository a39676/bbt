package demo.autoTestBase.testEvent.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;

@Component
public class TestEventAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(TestEvent te) {
		if (te == null) {
			return;
		}
		JSONObject json = testEventPOToJSON(te);
		rabbitTemplate.convertAndSend(TestEventOptionConstant.testEventQueue, json.toString());
	}

	private JSONObject testEventPOToJSON(TestEvent te) {
		JSONObject json = new JSONObject();

		if (te.getId() != null) {
			json.put("id", te.getId());
		}
		if (te.getProjectId() != null) {
			json.put("projectId", te.getProjectId());
		}
		if (te.getCaseId() != null) {
			json.put("caseId", te.getCaseId());
		}
		if (te.getProcessId() != null) {
			json.put("processId", te.getProcessId());
		}
		if (te.getModuleId() != null) {
			json.put("moduleId", te.getModuleId());
		}
		if (te.getEventName() != null) {
			json.put("eventName", te.getEventName());
		}
		if (te.getRemark() != null) {
			json.put("remark", te.getRemark());
		}
		if (te.getParameterFilePath() != null) {
			json.put("parameterFilePath", te.getParameterFilePath());
		}
		if (te.getReportPath() != null) {
			json.put("reportPath", te.getReportPath());
		}
		if (te.getCreateTime() != null) {
			json.put("createTime", localDateTimeHandler.dateToStr(te.getCreateTime()));
		}
		if (te.getAppointment() != null) {
			json.put("appointment", localDateTimeHandler.dateToStr(te.getAppointment()));
		}
		if (te.getStartTime() != null) {
			json.put("startTime", localDateTimeHandler.dateToStr(te.getStartTime()));
		}
		if (te.getEndTime() != null) {
			json.put("endTime", localDateTimeHandler.dateToStr(te.getEndTime()));
		}
		if (te.getIsPass() != null) {
			json.put("isPass", String.valueOf(te.getIsPass()));
		}
		if (te.getIsDelete() != null) {
			json.put("isDelete", String.valueOf(te.getIsDelete()));
		}

		return json;
	}

}
