package demo.scriptCore.scheduleClawing.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.cryptoCoin.pojo.type.CryptoCoinFlowType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.mq.sender.CryptoCoinDailyDataAckProducer;
import demo.scriptCore.scheduleClawing.pojo.result.CryptoCoinDailyDataResult;
import demo.scriptCore.scheduleClawing.service.CryptoCoinPriceService;
import demo.scriptCore.scheduleClawing.service.CryptoCompareService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;

@Service
public class CryptoCoinPriceServiceImpl extends AutomationTestCommonService implements CryptoCoinPriceService {

	@Autowired
	private CryptoCoinDailyDataAckProducer cryptoCoinDailyDataAckProducer;
	@Autowired
	private CryptoCompareService cryptoCompareService;

	@Override
	public TestEventBO cryptoCoinDailyDataAPI(TestEventBO tbo) {

		CryptoCoinFlowType flowType = CryptoCoinFlowType.DAILY_DATA;
		JsonReportOfCaseDTO caseReport = buildCaseReportDTO(flowType.getFlowName());
		
		try {
			if (StringUtils.isBlank(tbo.getParamStr())) {
				reportService.caseReportAppendContent(caseReport, "参数文件读取异常");
				throw new Exception();
			}

			CryptoCoinDailyDataQueryDTO paramDTO = auxTool.buildParamDTO(tbo, CryptoCoinDailyDataQueryDTO.class);
			
			if (paramDTO == null) {
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			if (StringUtils.isBlank(paramDTO.getApiKey())) {
				reportService.caseReportAppendContent(caseReport, "参数文件参数异常");
				throw new Exception();
			}
			// TODO 正在整理  分 crypto compare / binance api 
			
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
//				TODO need binance daily data API
			}
			
			CommonResult r = new CommonResult();
			
			if(apiResult.isSuccess()) {
				cryptoCoinDailyDataAckProducer.sendHistoryPrice(apiResult.getData());
			} else {
				/*
				 * TODO what to do when crypto coin daily data query fail
				 */
			}
			
			r.setSuccess(apiResult.isSuccess());
			r.setMessage(apiResult.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			reportService.caseReportAppendContent(caseReport, "异常: " + e);
		}
		
		return tbo;
	}

}
