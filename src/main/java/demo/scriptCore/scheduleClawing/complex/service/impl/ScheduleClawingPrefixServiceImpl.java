package demo.scriptCore.scheduleClawing.complex.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.cryptoCoin.service.CryptoCoinPriceService;
import demo.scriptCore.scheduleClawing.complex.service.HsbcService;
import demo.scriptCore.scheduleClawing.complex.service.ScheduleClawingPrefixService;
import demo.scriptCore.scheduleClawing.complex.service.UnderWayMonthTestService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRateService;
import demo.scriptCore.scheduleClawing.educationInfo.service.EducationInfoCollectionService;
import demo.scriptCore.scheduleClawing.jobInfo.service.V2exJobInfoCollectionService;
import demo.scriptCore.scheduleClawing.jobInfo.service.WuYiJobRefreshService;

@Service
public class ScheduleClawingPrefixServiceImpl extends AutomationTestCommonService
		implements ScheduleClawingPrefixService {

	@Autowired
	private WuYiJobRefreshService wuYiSign;
	@Autowired
	private EducationInfoCollectionService educationInfoCollectionService;
	@Autowired
	private V2exJobInfoCollectionService v2exJobInfoCollectionService;
	@Autowired
	private HsbcService hsbcService;
	@Autowired
	private UnderWayMonthTestService underWayMonthTestService;
	@Autowired
	private CryptoCoinPriceService cryptoCoinPriceService;
	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;

	@Override
	public TestEventBO runSubEvent(TestEventBO te) {
		Long caseId = te.getFlowId();
		
		ScheduleClawingType clawingType = ScheduleClawingType.getType(caseId);

		switch (clawingType) {
		case WU_YI_JOB: {
			return wuYiSign.clawing(te);
		}
		case EDUCATION_INFO: {
			return educationInfoCollectionService.clawing(te);
		}
		case V2EX_JOB_INFO: {
			return v2exJobInfoCollectionService.clawing(te);
		}
		case HSBC_WECHAT_PREREGIST: {
			return hsbcService.weixinPreReg(te);
		}
		case UNDER_WAY_MONTH_TEST: {
			return underWayMonthTestService.monthTest(te);
		}
		case CRYPTO_COIN: {
			return cryptoCoinPriceService.cryptoCoinDailyDataAPI(te);
		}
		case CURRENCY_EXCHANGE_RAGE: {
			return currencyExchangeRateService.getDailyData(te);
		}
		default:
			return new TestEventBO();
		}

	}

}