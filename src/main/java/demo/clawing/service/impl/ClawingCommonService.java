package demo.clawing.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.po.TestEvent;

public abstract class ClawingCommonService extends CommonService {

	@Autowired
	protected TestEventMapper eventMapper;
	
	protected int startEvent(TestEvent te) {
		return eventMapper.insertSelective(te);
	}
	
	protected int endEventSuccess(TestEvent te) {
		return endEvent(te, true);
	}
	
	protected int endEventFail(TestEvent te) {
		return endEvent(te, false);
	}
	
	private int endEvent(TestEvent te, boolean success) {
		te.setEndTime(LocalDateTime.now());
		te.setIsPass(success);
		return eventMapper.updateByPrimaryKeySelective(te);
	}
	
	protected boolean existsRuningEvent() {
		if(eventMapper.existsRuningEvent() == 0) {
			return false;
		} 
		return true;
	}

}