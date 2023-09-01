package demo.scriptCore.cryptoCoin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.cryptoCoin.mq.sender.CryptoCoinDailyDataAckProducer;
import demo.scriptCore.cryptoCoin.pojo.result.CryptoCoinDailyDataResult;
import demo.scriptCore.cryptoCoin.service.BinanceService;
import demo.scriptCore.cryptoCoin.service.CryptoCoinPriceService;
import demo.scriptCore.cryptoCoin.service.CryptoCompareService;
import demo.selenium.service.impl.AutomationTestCommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;

@Service
public class CryptoCoinPriceServiceImpl extends AutomationTestCommonService implements CryptoCoinPriceService {

	@Autowired
	private CryptoCoinDailyDataAckProducer cryptoCoinDailyDataAckProducer;
	@Autowired
	private CryptoCompareService cryptoCompareService;
	@Autowired
	private BinanceService binanceService;

	@Override
	public TestEventBO cryptoCoinDailyDataAPI(TestEventBO tbo) {

		ScheduleClawingType flowType = ScheduleClawingType.CRYPTO_COIN;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(flowType.getFlowName());
		
		try {
			if (StringUtils.isBlank(tbo.getParamStr())) {
				reportService.caseReportAppendContent(caseReport, "参数文件读取异常");
				throw new Exception();
			}

			CryptoCoinDailyDataQueryDTO paramDTO = buildTestEventParamFromJsonCustomization(tbo.getParamStr(), CryptoCoinDailyDataQueryDTO.class);
			
			if (paramDTO == null) {
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			if (StringUtils.isBlank(paramDTO.getApiKey())) {
				reportService.caseReportAppendContent(caseReport, "参数文件参数异常");
				throw new Exception();
			}
			
			/*
			 * 2021-08-13
			 * 暂时无精力再修缮
			 * 能用就算的部分代码
			 * 请勿以此为模板
			 */
			CryptoCoinDailyDataResult apiResult = null;
			
			if(paramDTO.getDataSourceCode() == null || CryptoCoinDataSourceType.CRYPTO_COMPARE.getCode().equals(paramDTO.getDataSourceCode())) {
				apiResult = cryptoCompareService.cryptoCoinDailyDataAPI(tbo, paramDTO);
			} else if(CryptoCoinDataSourceType.BINANCE.getCode().equals(paramDTO.getDataSourceCode())){
				apiResult = binanceService.cryptoCoinDailyDataAPI(tbo, paramDTO);
			}
			
			CommonResult r = new CommonResult();
			
			cryptoCoinDailyDataAckProducer.sendHistoryPrice(apiResult.getData());
			
			r.setSuccess(apiResult.isSuccess());
			r.setMessage(apiResult.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			reportService.caseReportAppendContent(caseReport, "异常: " + e);
		}
		
		return tbo;
	}

}
