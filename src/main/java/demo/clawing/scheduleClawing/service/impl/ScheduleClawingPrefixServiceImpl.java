package demo.clawing.scheduleClawing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.CryptoCoinPriceService;
import demo.clawing.scheduleClawing.service.LiePinDailySignService;
import demo.clawing.scheduleClawing.service.PreciousMetalsPriceService;
import demo.clawing.scheduleClawing.service.WuYiJobRefreshService;

@Service
public class ScheduleClawingPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {

	@Autowired
	private WuYiJobRefreshService wuYiSign;
	@Autowired
	private LiePinDailySignService liePinSign;
	@Autowired
	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
	@Autowired
	private PreciousMetalsPriceService preciousMetalsPriceService;
	@Autowired
	private CryptoCoinPriceService cryptoCoinPriceService;


	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();

		if (ScheduleClawingType.WU_YI_JOB.getId().equals(caseId)) {
			return wuYiSign.clawing(te);
		} else if (ScheduleClawingType.LIE_PIN.getId().equals(caseId)) {
			return liePinSign.dailySign(te);
		} else if (ScheduleClawingType.MAI_MAI.getId().equals(caseId)) {
			return maiMaiLocalClawingServiceImpl.clawing(te);
		} else if (ScheduleClawingType.PRECIOUS_METAL_PRICE.getId().equals(caseId)) {
			return preciousMetalsPriceService.goldPriceOrgAPI(te);
		} else if (ScheduleClawingType.CRYPTO_COIN_MINUTE_DATA.getId().equals(caseId)) {
			return cryptoCoinPriceService.cryptoCoinMinuteDataAPI(te);
		} else if (ScheduleClawingType.CRYPTO_COIN_DAILY_DATA.getId().equals(caseId)) {
			return cryptoCoinPriceService.cryptoCoinDailyDataAPI(te);
		}

		return new CommonResultBBT();
	}
}
