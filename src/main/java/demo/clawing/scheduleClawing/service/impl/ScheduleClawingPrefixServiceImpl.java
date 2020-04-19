package demo.clawing.scheduleClawing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.CdBaoDailySignService;
import demo.clawing.scheduleClawing.service.LiePinDailySignService;
import demo.clawing.scheduleClawing.service.WuYiJobRefreshService;

@Service
public class ScheduleClawingPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {

	@Autowired
	private WuYiJobRefreshService wuYiSign;
	@Autowired
	private LiePinDailySignService liePinSign;
	@Autowired
	private CdBaoDailySignService cdBao;
	@Autowired
	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		
		if (ScheduleClawingType.wuYiJob.getId().equals(caseId)) {
			return wuYiSign.clawing(te);
		} else if (ScheduleClawingType.liePin.getId().equals(caseId)) {
			return liePinSign.dailySign(te);
		} else if (ScheduleClawingType.cdBao.getId().equals(caseId)) {
			return cdBao.dailySign(te);
		} else if(ScheduleClawingType.maiMai.getId().equals(caseId)) {
			return maiMaiLocalClawingServiceImpl.clawing(te);
		}
		return new CommonResultBBT();
	}
}