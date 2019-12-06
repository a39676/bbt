package demo.clawing.dailySign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import demo.clawing.dailySign.pojo.type.DailySignCaseType;
import demo.clawing.dailySign.service.DailySignPrefixService;
import demo.clawing.dailySign.service.QuQiDailySignService;

@Service
public class DailySignPrefixServiceImpl extends CommonService implements DailySignPrefixService {

	@Autowired
	private QuQiDailySignService quqiSign;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		
		if (DailySignCaseType.quqi.getId().equals(caseId)) {
			return quqiSign.clawing(te);
		}
		return new CommonResultBBT();
	}
}
