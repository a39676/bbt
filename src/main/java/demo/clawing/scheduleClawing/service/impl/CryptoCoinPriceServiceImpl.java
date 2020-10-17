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
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.scheduleClawing.mq.sender.CroptoCoinTransmissionAckProducer;
import demo.clawing.scheduleClawing.pojo.bo.CryptoCoinHistoryPriceParamBO;
import demo.clawing.scheduleClawing.pojo.bo.CryptoCoinNewPriceParamBO;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.CryptoCoinPriceService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceSubDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinNewPriceDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinPriceServiceImpl extends SeleniumCommonService implements CryptoCoinPriceService {

	@Autowired
	private CroptoCoinTransmissionAckProducer croptoCoinTransmissionAckProducer;

	private String cryptoCoinNewPriceCollect = "cryptoCoinNewPriceCollect";
	private String newCryptoCoinPriceCollectingParam = "newCryptoCoinPriceCollectingParam.json";
	
	private String cryptoCoinHistoryPriceCollect = "cryptoCoinHistoryPriceCollect";
	private String historyCryptoCoinPriceCollectingParam = "historyCryptoCoinPriceCollectingParam.json";

	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType cryptoCoinNewPrice = ScheduleClawingType.cryptoCoinNewPrice;
	private ScheduleClawingType cryptoCoinHistoryPrice = ScheduleClawingType.cryptoCoinHistoryPrice;

	private TestEvent buildNewPriceCollectingEvent() {
		String paramterFolderPath = getParameterSaveingPath(cryptoCoinNewPriceCollect);
		File paramterFile = new File(paramterFolderPath + File.separator + newCryptoCoinPriceCollectingParam);
		if (!paramterFile.exists()) {
//			TODO
			return null;
		}
		
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(cryptoCoinNewPrice.getId());
		bo.setEventName(cryptoCoinNewPrice.getEventName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertNewCryptoCoinPriceEvent() {
		TestEvent te = buildNewPriceCollectingEvent();
		return testEventService.insertTestEvent(te);
	}
	
	private TestEvent buildHistoryPriceCollectingEvent() {
		String paramterFolderPath = getParameterSaveingPath(cryptoCoinHistoryPriceCollect);
		File paramterFile = new File(paramterFolderPath + File.separator + historyCryptoCoinPriceCollectingParam);
		if (!paramterFile.exists()) {
//			TODO
			return null;
		}
		
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(cryptoCoinHistoryPrice.getId());
		bo.setEventName(cryptoCoinHistoryPrice.getEventName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertHistoryCryptoCoinPriceEvent() {
		TestEvent te = buildHistoryPriceCollectingEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT cryptoCoinNewPriceAPI(TestEvent te) {
		JsonReportDTO reportDTO = new JsonReportDTO();
		String reportOutputFolderPath = getReportOutputPath(cryptoCoinNewPriceCollect);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		CommonResultBBT r = cryptoCoinNewPriceAPI(te, reportDTO);

		return r;
	}

	private CommonResultBBT cryptoCoinNewPriceAPI(TestEvent te, JsonReportDTO reportDTO) {
		CommonResultBBT r = new CommonResultBBT();

		// example: https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD,CNY
		String cryptoCoinApiUrlModel = "https://min-api.cryptocompare.com/data/price?fsym=%s&tsyms=%s";
		HttpUtil h = new HttpUtil();

		try {
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			CryptoCoinNewPriceParamBO clawingOptionBO = null;
			try {
				clawingOptionBO = new Gson().fromJson(jsonStr, CryptoCoinNewPriceParamBO.class);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(clawingOptionBO == null) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			
			String httpResponse = null;
			JSONObject json = null;
			CryptoCoinNewPriceDTO cryptoCoinPriceDTO = null;

			for(String coinTypeName : clawingOptionBO.getCoinType()) {
				for(String currencyName : clawingOptionBO.getCurrency()) {
					httpResponse = h.sendGet(String.format(cryptoCoinApiUrlModel, coinTypeName, currencyName));
					json = JSONObject.fromObject(httpResponse);
					jsonReporter.appendContent(reportDTO, json.toString());
					cryptoCoinPriceDTO = new CryptoCoinNewPriceDTO();
					cryptoCoinPriceDTO.setCroptoCoinName(coinTypeName);
					cryptoCoinPriceDTO.setCurrency(currencyName);
					cryptoCoinPriceDTO.setPrice(json.getDouble(currencyName));
					croptoCoinTransmissionAckProducer.sendNewPrice(cryptoCoinPriceDTO);
				}
			}
			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			jsonReporter.appendContent(reportDTO, "异常: " + e);

		} finally {
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}

		return r;
	}

	@Override
	public CommonResultBBT cryptoCoinHistoryPriceAPI(TestEvent te) {
		JsonReportDTO reportDTO = new JsonReportDTO();
		String reportOutputFolderPath = getReportOutputPath(cryptoCoinHistoryPriceCollect);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		CommonResultBBT r = cryptoCoinHistoryPriceAPI(te, reportDTO);

		return r;
	}

	private CommonResultBBT cryptoCoinHistoryPriceAPI(TestEvent te, JsonReportDTO reportDTO) {
		CommonResultBBT r = new CommonResultBBT();
		
		// example: https://min-api.cryptocompare.com/data/v2/histominute?fsym=ETH&tsym=USD&limit=10
		String cryptoCoinApiUrlModel = "https://min-api.cryptocompare.com/data/v2/histominute?fsym=%s&tsym=%s&limit=12";
		HttpUtil h = new HttpUtil();

		try {
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			CryptoCoinHistoryPriceParamBO clawingOptionBO = null;
			try {
				clawingOptionBO = new Gson().fromJson(jsonStr, CryptoCoinHistoryPriceParamBO.class);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(clawingOptionBO == null) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			
			String httpResponse = null;
			CryptoCoinHistoryPriceDTO mainDTO = null;
			List<CryptoCoinHistoryPriceSubDTO> subDataList = null;

			for(String coinTypeName : clawingOptionBO.getCoinType()) {
				for(String currencyName : clawingOptionBO.getCurrency()) {
					mainDTO = new CryptoCoinHistoryPriceDTO();
					
					httpResponse = h.sendGet(String.format(cryptoCoinApiUrlModel, coinTypeName, currencyName));
					jsonReporter.appendContent(reportDTO, httpResponse);
					
					subDataList = handleCryptoCoinHistoryResponse(httpResponse, coinTypeName, currencyName);
					mainDTO.setPriceHistoryData(subDataList);
					
					croptoCoinTransmissionAckProducer.sendHistoryPrice(mainDTO);
				}
			}
			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			jsonReporter.appendContent(reportDTO, "异常: " + e);

		} finally {
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}

		return r;
	}

	
	private List<CryptoCoinHistoryPriceSubDTO> handleCryptoCoinHistoryResponse(String response, String coinName, String currencyName) {
		List<CryptoCoinHistoryPriceSubDTO> list = new ArrayList<>();
		JSONObject json = JSONObject.fromObject(response);
		
		if(!"Success".equals(json.getString("Response"))) {
			return list;
		}
		
		JSONArray dataArray = json.getJSONObject("Data").getJSONArray("Data");
		JSONObject subJson = null;
		CryptoCoinHistoryPriceSubDTO priceDataDTO = null;
		
		for(int i = 0; i < dataArray.size(); i++) {
			subJson = (JSONObject) dataArray.get(i);
			priceDataDTO = new CryptoCoinHistoryPriceSubDTO();
			priceDataDTO.setTime(localDateTimeHandler.dateToLocalDateTime(new Date(subJson.getLong("time") * 1000L)));
			priceDataDTO.setCoinType(coinName);
			priceDataDTO.setCurrencyType(currencyName);
			priceDataDTO.setStart(subJson.getDouble("open"));
			priceDataDTO.setEnd(subJson.getDouble("close"));
			priceDataDTO.setHigh(subJson.getDouble("high"));
			priceDataDTO.setLow(subJson.getDouble("low"));
			list.add(priceDataDTO);
		}
		
		return list;
	}
}
