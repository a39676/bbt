package demo.clawing.dailySign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import demo.clawing.dailySign.pojo.type.DailySignCaseType;
import demo.clawing.dailySign.service.DailySignPrefixService;
import demo.clawing.dailySign.service.LiePinDailySignService;
import demo.clawing.dailySign.service.QuQiDailySignService;
import demo.clawing.dailySign.service.WuYiJobDailySignService;

@Service
public class DailySignPrefixServiceImpl extends CommonService implements DailySignPrefixService {

	@Autowired
	private QuQiDailySignService quqiSign;
	@Autowired
	private WuYiJobDailySignService wuYiSign;
	@Autowired
	private LiePinDailySignService liePinSign;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		
		if (DailySignCaseType.quqi.getId().equals(caseId)) {
			return quqiSign.clawing(te);
		} else if (DailySignCaseType.wuYiJob.getId().equals(caseId)) {
			return wuYiSign.dailySign(te);
		} else if (DailySignCaseType.liePin.getId().equals(caseId)) {
			return liePinSign.dailySign(te);
		}
		return new CommonResultBBT();
	}
}
