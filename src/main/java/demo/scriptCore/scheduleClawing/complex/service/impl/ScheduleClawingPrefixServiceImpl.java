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

		if (ScheduleClawingType.WU_YI_JOB.getId().equals(caseId)) {
			return wuYiSign.clawing(te);
		} else if (ScheduleClawingType.EDUCATION_INFO.getId().equals(caseId)) {
			return educationInfoCollectionService.clawing(te);
		} else if (ScheduleClawingType.V2EX_JOB_INFO.getId().equals(caseId)) {
			return v2exJobInfoCollectionService.clawing(te);
		} else if (ScheduleClawingType.HSBC_WECHAT_PREREGIST.getId().equals(caseId)) {
			return hsbcService.weixinPreReg(te);
		} else if (ScheduleClawingType.UNDER_WAY_MONTH_TEST.getId().equals(caseId)) {
			return underWayMonthTestService.monthTest(te);
		} else if (ScheduleClawingType.CRYPTO_COIN.getId().equals(caseId)) {
			return cryptoCoinPriceService.cryptoCoinDailyDataAPI(te);
		} else if (ScheduleClawingType.CURRENCY_EXCHANGE_RAGE.getId().equals(caseId)) {
			currencyExchangeRateService.getDailyData(te);
		}

		return new TestEventBO();
	}

}
