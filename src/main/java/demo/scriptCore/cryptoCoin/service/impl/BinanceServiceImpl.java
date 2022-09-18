package demo.scriptCore.cryptoCoin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.cryptoCoin.pojo.result.CryptoCoinDailyDataResult;
import demo.scriptCore.cryptoCoin.service.BinanceService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataSubDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;
import net.sf.json.JSONArray;
import toolPack.httpHandel.HttpUtil;

@Service
public class BinanceServiceImpl extends AutomationTestCommonService implements BinanceService {

	@Override
	public CryptoCoinDailyDataResult cryptoCoinDailyDataAPI(TestEventBO tbo, CryptoCoinDailyDataQueryDTO paramDTO) {
		CryptoCoinDailyDataResult r = new CryptoCoinDailyDataResult();

		ScheduleClawingType flowType = ScheduleClawingType.CRYPTO_COIN;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(flowType.getFlowName());

		// example:
		// https://api.binance.com/api/v3/klines?symbol=BNBBTC&interval=1d&startTime=1590710400000&endTime=1590796800000&limit=500
		String cryptoCoinApiUrlModel = "https://api.binance.com/api/v3/klines?symbol=%s&interval=1d&limit=%s";
		HttpUtil h = new HttpUtil();

		try {
			String httpResponse = null;
			CryptoCoinDataDTO mainDTO = new CryptoCoinDataDTO();
			List<CryptoCoinDataSubDTO> subDataList = null;

			httpResponse = h.sendGet(String.format(cryptoCoinApiUrlModel, paramDTO.getCoinName().toUpperCase(),
					paramDTO.getCurrencyName().toUpperCase(), paramDTO.getCounting()));
			reportService.caseReportAppendContent(caseReport, httpResponse);

			subDataList = handleCryptoCoinDataResponse(httpResponse, paramDTO.getCoinName(),
					paramDTO.getCurrencyName());
			mainDTO.setPriceHistoryData(subDataList);
			mainDTO.setCryptoCoinTypeName(paramDTO.getCoinName());
			mainDTO.setCurrencyName(paramDTO.getCurrencyName());
			mainDTO.setDataSourceCode(CryptoCoinDataSourceType.BINANCE.getCode());

			r.setData(mainDTO);
			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			reportService.caseReportAppendContent(caseReport, "异常: " + e);

		} finally {
			/*
			 * crypto coin report 未保存 report
			 * tbo.getReport().getCaseReportList().add(caseReport);
			 */
			sendAutomationTestResult(tbo);
		}

		return r;
	}

	private List<CryptoCoinDataSubDTO> handleCryptoCoinDataResponse(String response, String coinName,
			String currencyName) {
		List<CryptoCoinDataSubDTO> resultList = new ArrayList<>();
		JSONArray dataArray = JSONArray.fromObject(response);
		JSONArray subJson = null;
		CryptoCoinDataSubDTO priceDataDTO = null;

		Date tmpDate = null;
		for (int i = 0; i < dataArray.size(); i++) {
			subJson = dataArray.getJSONArray(i);
			priceDataDTO = new CryptoCoinDataSubDTO();
			tmpDate = new Date(subJson.getInt(0));
			priceDataDTO.setTime(localDateTimeHandler.dateToStr(localDateTimeHandler.dateToLocalDateTime(tmpDate)));
			priceDataDTO.setStart(subJson.getDouble(1));
			priceDataDTO.setHigh(subJson.getDouble(2));
			priceDataDTO.setLow(subJson.getDouble(3));
			priceDataDTO.setEnd(subJson.getDouble(4));
			priceDataDTO.setVolume(subJson.getDouble(5));
			priceDataDTO.setCoinType(coinName);
			priceDataDTO.setCurrencyType(currencyName);
			resultList.add(priceDataDTO);
		}

		return resultList;
	}

}
