package demo.scriptCore.cryptoCoin.service.impl;

import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.cryptoCoin.pojo.result.CryptoCoinDailyDataResult;
import demo.scriptCore.cryptoCoin.service.CryptoCompareService;
import demo.selenium.service.impl.AutomationTestCommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

@Service
public class CryptoCompareServiceImpl extends AutomationTestCommonService implements CryptoCompareService {

//	2024-04-23 将所有价格API 转移到 crypto coin monitor 项目上

	@Override
	public CryptoCoinDailyDataResult cryptoCoinDailyDataAPI(TestEventBO tbo, CryptoCoinDailyDataQueryDTO paramDTO) {
		CryptoCoinDailyDataResult r = new CryptoCoinDailyDataResult();

//		ScheduleClawingType flowType = ScheduleClawingType.CRYPTO_COIN;
//		JsonReportOfCaseDTO caseReport = initCaseReportDTO(flowType.getFlowName());
//
//		// example:
//		// https://min-api.cryptocompare.com/data/v2/histoday?fsym=BTC&tsym=USD&limit=10
//		String cryptoCoinApiUrlModel = "https://min-api.cryptocompare.com/data/v2/histoday?fsym=%s&tsym=%s&limit=%s&api_key=%s";
//		HttpUtil h = new HttpUtil();
//
//		try {
//			String httpResponse = null;
//			CryptoCoinDataDTO mainDTO = new CryptoCoinDataDTO();
//			List<CryptoCoinDataSubDTO> subDataList = null;
//
//			httpResponse = h.sendGet(String.format(cryptoCoinApiUrlModel, paramDTO.getCoinName(),
//					paramDTO.getCurrencyName(), paramDTO.getCounting(), paramDTO.getApiKey()));
//			reportService.caseReportAppendContent(caseReport, httpResponse);
//
//			subDataList = handleCryptoCoinDataResponse(httpResponse, paramDTO.getCoinName(),
//					paramDTO.getCurrencyName());
//			mainDTO.setPriceHistoryData(subDataList);
//			mainDTO.setCryptoCoinTypeName(paramDTO.getCoinName());
//			mainDTO.setCurrencyName(paramDTO.getCurrencyName());
//			mainDTO.setDataSourceCode(CryptoCoinDataSourceType.CRYPTO_COMPARE.getCode());
//
//			r.setData(mainDTO);
//			r.setIsSuccess();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			reportService.caseReportAppendContent(caseReport, "异常: " + e);
//
//		} finally {
//			/*
//			 * crypto coin report 未保存 report
//			 * tbo.getReport().getCaseReportList().add(caseReport);
//			 */
//			sendAutomationTestResult(tbo);
//		}

		return r;
	}

//	private List<CryptoCoinDataSubDTO> handleCryptoCoinDataResponse(String response, String coinName,
//			String currencyName) {
//		List<CryptoCoinDataSubDTO> list = new ArrayList<>();
//		JSONObject json = JSONObject.fromObject(response);
//
//		if (!"Success".equals(json.getString("Response"))) {
//			return list;
//		}
//
//		JSONArray dataArray = json.getJSONObject("Data").getJSONArray("Data");
//		JSONObject subJson = null;
//		CryptoCoinDataSubDTO priceDataDTO = null;
//
//		Date tmpDate = null;
//		for (int i = 0; i < dataArray.size(); i++) {
//			subJson = (JSONObject) dataArray.get(i);
//			priceDataDTO = new CryptoCoinDataSubDTO();
//			priceDataDTO.setCoinType(coinName);
//			priceDataDTO.setCurrencyType(currencyName);
//			priceDataDTO.setStart(subJson.getDouble("open"));
//			priceDataDTO.setEnd(subJson.getDouble("close"));
//			priceDataDTO.setHigh(subJson.getDouble("high"));
//			priceDataDTO.setLow(subJson.getDouble("low"));
//			priceDataDTO.setVolume(subJson.getDouble("volumeto"));
//			tmpDate = new Date(subJson.getLong("time") * 1000L);
//			priceDataDTO.setTime(localDateTimeHandler.dateToStr(localDateTimeHandler.dateToLocalDateTime(tmpDate)));
//			list.add(priceDataDTO);
//		}
//
//		return list;
//	}

}
