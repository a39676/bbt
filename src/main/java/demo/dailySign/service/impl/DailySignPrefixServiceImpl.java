package demo.dailySign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import demo.dailySign.pojo.type.DailySignCaseType;
import demo.dailySign.service.DailySignPrefixService;
import demo.dailySign.service.QuQiDailySignService;
import demo.testCase.pojo.po.TestEvent;

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
		return null;
	}
}
