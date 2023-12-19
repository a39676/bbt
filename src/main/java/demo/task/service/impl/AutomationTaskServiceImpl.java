package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.baseCommon.service.CommonService;
import demo.task.service.impl.mq.producer.TestEventInsertAckProducer;

public class AutomationTaskServiceImpl extends CommonService {

	@Autowired
	private TestEventInsertAckProducer testEventInsertAckProducer;
	
	@Scheduled(cron="20 19 09 * * *")
	@Scheduled(cron="20 19 12 * * *")
	@Scheduled(cron="20 19 17 * * *")
	@Scheduled(cron="20 19 20 * * *")
	@Scheduled(cron="20 19 23 * * *")
	public void sendNormalDataTask() {
		sendEducationInfomationCollectionTask();
		sendV2exJobInfomationCollectionTask();
	}
	
	private void sendEducationInfomationCollectionTask() {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		dto.setFlowType(ScheduleClawingType.EDUCATION_INFO.getId());
		dto.setTestEventId(snowFlake.getNextId());
		testEventInsertAckProducer.send(dto);
	}
	
	private void sendV2exJobInfomationCollectionTask() {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		dto.setFlowType(ScheduleClawingType.V2EX_JOB_INFO.getId());
		dto.setTestEventId(snowFlake.getNextId());
		testEventInsertAckProducer.send(dto);
	}
}
