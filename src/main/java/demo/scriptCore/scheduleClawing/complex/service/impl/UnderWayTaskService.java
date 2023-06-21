package demo.scriptCore.scheduleClawing.complex.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import demo.scriptCore.scheduleClawing.complex.service.UnderWayService;

@Component
public class UnderWayTaskService extends CommonService {

	@Autowired
	private UnderWayService underWayService;

	@Scheduled(fixedDelay = 60000)
	public void checkTrainProjectDoneRequest() {
		underWayService.checkAndSendCourseDoneRequest();
	}

}
