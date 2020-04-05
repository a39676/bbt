package demo.clawing.dailySign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import demo.clawing.dailySign.pojo.type.DailySignCaseType;
import demo.clawing.dailySign.service.CdBaoDailySignService;
import demo.clawing.dailySign.service.LiePinDailySignService;
import demo.clawing.dailySign.service.WuYiJobRefreshService;

@Service
public class DailySignPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {

	@Autowired
	private WuYiJobRefreshService wuYiSign;
	@Autowired
	private LiePinDailySignService liePinSign;
	@Autowired
	private CdBaoDailySignService cdBao;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		
		if (DailySignCaseType.wuYiJob.getId().equals(caseId)) {
			return wuYiSign.dailySign(te);
		} else if (DailySignCaseType.liePin.getId().equals(caseId)) {
			return liePinSign.dailySign(te);
		} else if (DailySignCaseType.cdBao.getId().equals(caseId)) {
			return cdBao.dailySign(te);
		}
		return new CommonResultBBT();
	}
}
