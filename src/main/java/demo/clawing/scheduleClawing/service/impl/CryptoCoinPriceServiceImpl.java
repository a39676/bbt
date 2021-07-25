package demo.clawing.scheduleClawing.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import at.report.pojo.dto.JsonReportDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.scheduleClawing.mq.sender.CryptoCoinDailyDataAckProducer;
import demo.clawing.scheduleClawing.mq.sender.CryptoCoinMinuteDataAckProducer;
import demo.clawing.scheduleClawing.pojo.bo.CryptoCompareDataAPIParamBO;
import demo.clawing.scheduleClawing.pojo.result.CryptoCoinDailyDataResult;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.CryptoCoinPriceService;
import demo.clawing.scheduleClawing.service.CryptoCompareService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataSubDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinPriceServiceImpl extends SeleniumCommonService implements CryptoCoinPriceService {

	@Autowired
	private CryptoCoinMinuteDataAckProducer croptoCoinMinuteDataAckProducer;
	@Autowired
	private CryptoCoinDailyDataAckProducer cryptoCoinDailyDataAckProducer;
	@Autowired
	private CryptoCompareService cryptoCompareService;

	private TestModuleType testModuleType = TestModuleType.scheduleClawing;

	private String cryptoCoinMinuteDataCollect = "cryptoCoinMinuteDataCollect";
	private String cryptoCoinMinuteDataCollectParam = "cryptoCoinMinuteDataCollectParam.json";
	private ScheduleClawingType cryptoCoinMinuteData = ScheduleClawingType.CRYPTO_COIN_MINUTE_DATA;

	private String cryptoCoinDailyDataCollect = "cryptoCoinDailyDataCollect";
	private String cryptoCoinDailyDataCollectParam = "cryptoCoinDailyDataCollectParam.json";
	private ScheduleClawingType cryptoCoinDailyData = ScheduleClawingType.CRYPTO_COIN_DAILY_DATA;


	private TestEvent buildCryptoCoinMinuteDataCollectingEvent() {
		String paramterFolderPath = getParameterSaveingPath(cryptoCoinMinuteDataCollect);
		File paramterFile = new File(paramterFolderPath + File.separator + cryptoCoinMinuteDataCollectParam);
		if (!paramterFile.exists()) {
//			TODO test event if paramterFile NOT exists
			return null;
		}

		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(cryptoCoinMinuteData.getId());
		bo.setEventName(cryptoCoinMinuteData.getEventName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}

	private TestEvent buildCryptoCoinDailyDataCollectingEvent() {
		String paramterFolderPath = getParameterSaveingPath(cryptoCoinDailyDataCollect);
		File paramterFile = new File(paramterFolderPath + File.separator + cryptoCoinDailyDataCollectParam);
		if (!paramterFile.exists()) {
//			TODO test event if paramterFile NOT exists
			return null;
		}

		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(cryptoCoinDailyData.getId());
		bo.setEventName(cryptoCoinDailyData.getEventName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}
	
	@Override
	public InsertTestEventResult insertCryptoCoinMinuteDataCollectEvent() {
		TestEvent te = buildCryptoCoinMinuteDataCollectingEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public InsertTestEventResult insertCryptoCoinDailyDataCollectEvent(CryptoCoinDailyDataQueryDTO dto) {
		if(dto == null || StringUtils.isBlank(dto.getCoinName()) || dto.getCounting() <= 0) {
			return new InsertTestEventResult();
		}
		
		TestEvent te = buildCryptoCoinDailyDataCollectingEvent();
		if(StringUtils.isBlank(dto.getCurrencyName())) {
			dto.setCurrencyName(CurrencyType.USD.getName());
		}
		return testEventService.insertTestEvent(te, JSONObject.fromObject(dto));
	}

	@Override
	public CommonResultBBT cryptoCoinMinuteDataAPI(TestEvent te) {
		JsonReportDTO reportDTO = new JsonReportDTO();
		String reportOutputFolderPath = getReportOutputPath(cryptoCoinMinuteDataCollect);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		CommonResultBBT r = cryptoCoinMinuteDataAPI(te, reportDTO);

		return r;
	}

	@Override
	public CommonResultBBT cryptoCoinDailyDataAPI(TestEvent te) {
		// TODO 正在整理  分 crypto compare / binance api 
		
		CryptoCoinDailyDataQueryDTO queryDTO = null;
		
		try {
			JSONObject paramJson = tryFindParam(te.getId());
			queryDTO = new Gson().fromJson(paramJson.toString(), CryptoCoinDailyDataQueryDTO.class);
		} catch (Exception e) {
		}
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		String reportOutputFolderPath = getReportOutputPath(cryptoCoinDailyDataCollect);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		CryptoCoinDailyDataResult apiResult = null;
		
		if(queryDTO == null || queryDTO.getDataSourceCode() == null || CryptoCoinDataSourceType.CRYPTO_COMPARE.getCode().equals(queryDTO.getDataSourceCode())) {
			apiResult = cryptoCompareService.cryptoCoinDailyDataAPI(te, reportDTO);
		} else if(CryptoCoinDataSourceType.BINANCE.getCode().equals(queryDTO.getDataSourceCode())){
//			TODO need binance daily data API
		}
		
		CommonResultBBT r = new CommonResultBBT();
		
		if(apiResult.isSuccess()) {
			cryptoCoinDailyDataAckProducer.sendHistoryPrice(apiResult.getData());
			redisConnectService.deleteValByName(TestEventOptionConstant.TEST_EVENT_REDIS_PARAM_KEY_PREFIX + "_" + te.getId());
		} else {
			/*
			 * TODO what to do when crypto coin daily data query fail
			 */
		}
		
		r.setSuccess(apiResult.isSuccess());
		r.setMessage(apiResult.getMessage());
		
		return r;
	}

	private CommonResultBBT cryptoCoinMinuteDataAPI(TestEvent te, JsonReportDTO reportDTO) {
		CommonResultBBT r = new CommonResultBBT();

		// example:
		// https://min-api.cryptocompare.com/data/v2/histominute?fsym=ETH&tsym=USD&limit=10
		String cryptoCoinApiUrlModel = "https://min-api.cryptocompare.com/data/v2/histominute?fsym=%s&tsym=%s&limit=%s&api_key=%s";
		HttpUtil h = new HttpUtil();

		try {

			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if (StringUtils.isBlank(jsonStr)) {
				reportService.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}

			CryptoCompareDataAPIParamBO clawingOptionBO = null;
			try {
				clawingOptionBO = new Gson().fromJson(jsonStr, CryptoCompareDataAPIParamBO.class);
			} catch (Exception e) {
				reportService.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}

			if (clawingOptionBO == null) {
				reportService.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}

			if (clawingOptionBO.getLimit() == null || StringUtils.isBlank(clawingOptionBO.getApiKey())) {
				reportService.appendContent(reportDTO, "参数文件参数异常");
				throw new Exception();
			}

			String httpResponse = null;
			CryptoCoinDataDTO mainDTO = null;
			List<CryptoCoinDataSubDTO> subDataList = null;

			for (String currencyName : clawingOptionBO.getCurrency()) {
				for (String coinTypeName : clawingOptionBO.getCoinType()) {
					mainDTO = new CryptoCoinDataDTO();

					httpResponse = h.sendGet(String.format(cryptoCoinApiUrlModel, coinTypeName, currencyName,
							clawingOptionBO.getLimit(), clawingOptionBO.getApiKey()));
					reportService.appendContent(reportDTO, httpResponse);

					subDataList = handleCryptoCoinDataResponse(httpResponse, coinTypeName, currencyName);
					mainDTO.setPriceHistoryData(subDataList);
					mainDTO.setCryptoCoinTypeName(coinTypeName);
					mainDTO.setCurrencyName(currencyName);

					croptoCoinMinuteDataAckProducer.sendHistoryPrice(mainDTO);
				}
			}

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			reportService.appendContent(reportDTO, "异常: " + e);

		} finally {
			if (reportService.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
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
