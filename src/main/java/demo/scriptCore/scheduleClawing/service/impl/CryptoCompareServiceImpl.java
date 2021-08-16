package demo.scriptCore.scheduleClawing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import at.report.pojo.dto.JsonReportDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.scriptCore.scheduleClawing.pojo.bo.CryptoCompareDataAPIParamBO;
import demo.scriptCore.scheduleClawing.pojo.result.CryptoCoinDailyDataResult;
import demo.scriptCore.scheduleClawing.service.CryptoCompareService;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataSubDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCompareServiceImpl extends SeleniumCommonService implements CryptoCompareService {

	@Override
	public CryptoCoinDailyDataResult cryptoCoinDailyDataAPI(TestEvent te, JsonReportDTO reportDTO) {
		CryptoCoinDailyDataResult r = new CryptoCoinDailyDataResult();
		
		TestEventBO tbo = auxTool.beforeRunning(te);

		// example:
		// https://min-api.cryptocompare.com/data/v2/histoday?fsym=BTC&tsym=USD&limit=10
		String cryptoCoinApiUrlModel = "https://min-api.cryptocompare.com/data/v2/histoday?fsym=%s&tsym=%s&limit=%s&api_key=%s";
		HttpUtil h = new HttpUtil();

		try {

			String optionJsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if (StringUtils.isBlank(optionJsonStr)) {
				reportService.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}

			CryptoCompareDataAPIParamBO clawingOptionBO = null;
			try {
				clawingOptionBO = new Gson().fromJson(optionJsonStr, CryptoCompareDataAPIParamBO.class);
			} catch (Exception e) {
				reportService.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}

			if (clawingOptionBO == null) {
				reportService.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}

			if (StringUtils.isBlank(clawingOptionBO.getApiKey())) {
				reportService.appendContent(reportDTO, "参数文件参数异常");
				throw new Exception();
			}

			String httpResponse = null;
			CryptoCoinDataDTO mainDTO = new CryptoCoinDataDTO();
			List<CryptoCoinDataSubDTO> subDataList = null;
			
			String paramJsonStr = redisConnectService.getValByName(TestEventOptionConstant.TEST_EVENT_REDIS_PARAM_KEY_PREFIX + "_" + te.getId());
			if(StringUtils.isBlank(paramJsonStr)) {
				reportService.appendContent(reportDTO, "test event: " + te.getId() + ", " + te.getEventName() + ", 动态参数获取异常");
				return r;
			}
			
			CryptoCoinDailyDataQueryDTO dynamicParam = null;
			try {
				dynamicParam = new Gson().fromJson(paramJsonStr, CryptoCoinDailyDataQueryDTO.class); 
			} catch (Exception e) {
				reportService.appendContent(reportDTO, "test event: " + te.getId() + ", " + te.getEventName() + ", 动态参数获取异常");
				throw new Exception();
			}
			
			httpResponse = h.sendGet(String.format(cryptoCoinApiUrlModel, dynamicParam.getCoinName(), dynamicParam.getCurrencyName(),
					dynamicParam.getCounting(), clawingOptionBO.getApiKey()));
			reportService.appendContent(reportDTO, httpResponse);
			
			subDataList = handleCryptoCoinDataResponse(httpResponse, dynamicParam.getCoinName(), dynamicParam.getCurrencyName());
			mainDTO.setPriceHistoryData(subDataList);
			mainDTO.setCryptoCoinTypeName(dynamicParam.getCoinName());
			mainDTO.setCurrencyName(dynamicParam.getCurrencyName());
			
			r.setData(mainDTO);
			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			reportService.appendContent(reportDTO, "异常: " + e);

		} finally {
			saveReport(tbo);
		}

		return r;
	}
	
	private List<CryptoCoinDataSubDTO> handleCryptoCoinDataResponse(String response, String coinName,
			String currencyName) {
		List<CryptoCoinDataSubDTO> list = new ArrayList<>();
		JSONObject json = JSONObject.fromObject(response);

		if (!"Success".equals(json.getString("Response"))) {
			return list;
		}

		JSONArray dataArray = json.getJSONObject("Data").getJSONArray("Data");
		JSONObject subJson = null;
		CryptoCoinDataSubDTO priceDataDTO = null;

		Date tmpDate = null;
		for (int i = 0; i < dataArray.size(); i++) {
			subJson = (JSONObject) dataArray.get(i);
			priceDataDTO = new CryptoCoinDataSubDTO();
			priceDataDTO.setCoinType(coinName);
			priceDataDTO.setCurrencyType(currencyName);
			priceDataDTO.setStart(subJson.getDouble("open"));
			priceDataDTO.setEnd(subJson.getDouble("close"));
			priceDataDTO.setHigh(subJson.getDouble("high"));
			priceDataDTO.setLow(subJson.getDouble("low"));
			priceDataDTO.setVolume(subJson.getDouble("volumeto"));
			tmpDate = new Date(subJson.getLong("time") * 1000L);
			priceDataDTO.setTime(localDateTimeHandler.dateToStr(localDateTimeHandler.dateToLocalDateTime(tmpDate)));
			list.add(priceDataDTO);
		}

		return list;
	}

}
