package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;

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
		redisConnectService.setValByName(runningEventRedisKey, "true");
	}
	
	protected CommonResult endEvent(TestEventBO te) {
		redisConnectService.setValByName(runningEventRedisKey, "false");
		CommonResult endEventResult = new CommonResult();
		endEventResult.setIsSuccess();
		return endEventResult;
	}
	
	protected boolean existsRuningEvent() {
		String runningEventStatus = redisConnectService.getValByName(runningEventRedisKey);
		if("false".equals(runningEventStatus)) {
			return false;
		}
		return true;
	}

}
