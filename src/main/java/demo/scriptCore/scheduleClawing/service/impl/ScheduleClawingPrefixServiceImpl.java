package demo.scriptCore.scheduleClawing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.service.CommonService;
import demo.scriptCore.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.scriptCore.scheduleClawing.service.CryptoCoinPriceService;
import demo.scriptCore.scheduleClawing.service.WuYiJobRefreshService;

@Service
public class ScheduleClawingPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {

	@Autowired
	private WuYiJobRefreshService wuYiSign;
	@Autowired
	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
	@Autowired
	private CryptoCoinPriceService cryptoCoinPriceService;

	@Override
	public CommonResult runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();

		if (ScheduleClawingType.WU_YI_JOB.getId().equals(caseId)) {
			return wuYiSign.clawing(te);
		} else if (ScheduleClawingType.MAI_MAI.getId().equals(caseId)) {
			return maiMaiLocalClawingServiceImpl.clawing(te);
		} else if (ScheduleClawingType.CRYPTO_COIN_DAILY_DATA.getId().equals(caseId)) {
			return cryptoCoinPriceService.cryptoCoinDailyDataAPI(te);
		}

		return new CommonResult();
	}
}
