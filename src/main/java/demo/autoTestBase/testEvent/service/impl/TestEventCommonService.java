package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.baseCommon.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

public abstract class TestEventCommonService extends CommonService {

	@Autowired
	protected FileUtilCustom fileUtil;
	@Autowired
	protected AutomationTestConstantService constantService;

	protected String runningEventRedisKey = "runningEvent";

	protected void startEvent(TestEventBO te) {
		te.setStartTime(LocalDateTime.now());
		redisHashConnectService.setValByName(runningEventRedisKey, String.valueOf(te.getEventId()), te.getFlowName());
		if (te.getEventId() == null) {
			log.error("Get null event ID, flow name: " + te.getFlowName());
		}
	}

	protected CommonResult endEvent(TestEventBO te) {
		redisHashConnectService.deleteValByName(runningEventRedisKey, String.valueOf(te.getEventId()));
		CommonResult endEventResult = new CommonResult();
		endEventResult.setIsSuccess();
		return endEventResult;
	}

	protected boolean existsRuningEvent() {
		return redisHashConnectService.getSize(runningEventRedisKey) > 0;
	}

	protected boolean canInsertRuningEvent() {
		return redisHashConnectService.getSize(runningEventRedisKey) < constantService.getLimitOfRunningInTheSameTime();
	}
	
	protected List<String> getRunningEventList() {
		return redisHashConnectService.getValsByName(runningEventRedisKey);
		
	}

	protected void fixRuningEventStatus() {
		redisHashConnectService.deleteValByName(runningEventRedisKey);
	}
}
