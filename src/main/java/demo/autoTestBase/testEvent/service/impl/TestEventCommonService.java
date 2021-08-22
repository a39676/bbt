package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.mapper.TestEventMapper;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

public abstract class TestEventCommonService extends CommonService {

	@Autowired
	protected TestEventMapper eventMapper;
	@Autowired
	protected FileUtilCustom fileUtil;
	
	protected String runningEventRedisKey = "runningEvent";

	protected void startEvent(TestEvent te) {
		te.setStartTime(LocalDateTime.now());
		redisConnectService.setValByName(runningEventRedisKey, "true");
	}
	
	protected CommonResult endEvent(TestEvent te) {
		
		CommonResult endEventResult = new CommonResult();
		
		try {
			redisConnectService.setValByName(runningEventRedisKey, "false");
			int insertCount = eventMapper.insertSelective(te);
			
			if(insertCount > 0) {
				endEventResult.setIsSuccess();
			}
		} catch (Exception e) {
			log.error("end test event error: " + e.getLocalizedMessage());
		}
		
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
